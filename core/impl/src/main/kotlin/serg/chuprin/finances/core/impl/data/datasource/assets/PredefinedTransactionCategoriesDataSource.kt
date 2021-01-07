package serg.chuprin.finances.core.impl.data.datasource.assets

import android.content.Context
import android.content.res.AssetManager
import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.impl.data.model.PredefinedTransactionCategories
import java.io.BufferedReader
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class PredefinedTransactionCategoriesDataSource @Inject constructor(
    private val context: Context
) {

    private companion object {
        private const val FILE_INCOME_CATEGORIES = "income_transaction_categories.json"
        private const val FILE_INCOME_CATEGORIES_EN = "income_transaction_categories_en.json"
        private const val FILE_EXPENSE_CATEGORIES = "expense_transaction_categories.json"
        private const val FILE_EXPENSE_CATEGORIES_EN = "expense_transaction_categories_en.json"
    }

    suspend fun getCategories(): PredefinedTransactionCategories {
        return withContext(Dispatchers.IO) {
            try {
                // Retrieve all categories without reopening AssetManager.
                // It crashes for unknown reason otherwise.
                context.assets.use { assetManager ->
                    val locale = Locale.getDefault()
                    val predefinedTransactionCategories = PredefinedTransactionCategories(
                        incomeCategories = assetManager.getCategories(
                            getIncomeCategoriesFilename(locale)
                        ),
                        expenseCategories = assetManager.getCategories(
                            getExpenseCategoriesFilename(locale)
                        )
                    )
                    predefinedTransactionCategories
                }
            } catch (throwable: Throwable) {
                Timber.d(throwable) { "An error occurred when getting predefined categories" }
                PredefinedTransactionCategories(emptyList(), emptyList())
            }
        }
    }

    private fun AssetManager.getCategories(filename: String): List<TransactionCategoryAssetDto> {
        val json = open(filename).bufferedReader().use(BufferedReader::readText)
        val deserializer = ListSerializer(TransactionCategoryAssetDto.serializer())
        return Json.decodeFromString(deserializer, json).generateIds()
    }

    /**
     * We use categories from json file and all categories contains hardcoded ids.
     * It's required to generate new ids.
     */
    private fun List<TransactionCategoryAssetDto>.generateIds(): List<TransactionCategoryAssetDto> {

        // Group ba parent category id presence.
        val groupedByParentCategory = groupBy { dto -> dto.parentCategoryId == null }

        // Map of parent categories.
        // Key is original id, value is updated category with generated id.
        val parentCategories = groupedByParentCategory[true]
            .orEmpty()
            .associateBy(
                // Key is original category's id.
                keySelector = { dto -> dto.id },
                // Value is category itself with updated id.
                valueTransform = { dto -> dto.id = Id.createNew().value; dto }
            )

        // Map of child categories.
        // Each category's parent category id is updated using values from [parentCategories] map.
        // Id of category itself is generated.
        val childCategories = groupedByParentCategory[false]
            .orEmpty()
            .map { dto ->
                dto.apply {
                    id = Id.createNew().value
                    parentCategoryId = parentCategories.getValue(dto.parentCategoryId!!).id
                }
            }

        return parentCategories.values + childCategories
    }

    private fun getIncomeCategoriesFilename(locale: Locale): String {
        if (locale.language.contains("en", ignoreCase = true)) {
            return FILE_INCOME_CATEGORIES_EN
        }
        return FILE_INCOME_CATEGORIES
    }

    private fun getExpenseCategoriesFilename(locale: Locale): String {
        if (locale.language.contains("en", ignoreCase = true)) {
            return FILE_EXPENSE_CATEGORIES_EN
        }
        return FILE_EXPENSE_CATEGORIES
    }

}