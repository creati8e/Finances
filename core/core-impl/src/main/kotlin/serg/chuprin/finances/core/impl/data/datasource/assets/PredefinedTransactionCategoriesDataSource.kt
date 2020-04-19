package serg.chuprin.finances.core.impl.data.datasource.assets

import android.content.Context
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import serg.chuprin.finances.core.api.domain.model.Id
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
@OptIn(UnstableDefault::class)
class PredefinedTransactionCategoriesDataSource @Inject constructor(
    private val context: Context
) {

    private companion object {
        private const val FILE_INCOME_CATEGORIES = "income_transaction_categories.json"
        private const val FILE_EXPENSE_CATEGORIES = "expense_transaction_categories.json"
    }

    suspend fun getIncomeCategories(): List<TransactionCategoryAssetDto> {
        return getCategories(FILE_INCOME_CATEGORIES)
    }

    suspend fun getExpenseCategories(): List<TransactionCategoryAssetDto> {
        return getCategories(FILE_EXPENSE_CATEGORIES)
    }

    private suspend fun getCategories(filename: String): List<TransactionCategoryAssetDto> {
        return suspendCoroutine { continuation ->
            try {
                val json = context.assets.use { assetManager ->
                    val open = assetManager.open(filename)
                    String(ByteArray(open.available()).also { bytes -> open.read(bytes) })
                }
                val deserializer = ListSerializer(TransactionCategoryAssetDto.serializer())
                continuation.resume(Json.parse(deserializer, json).generateIds())
            } catch (throwable: Throwable) {
                continuation.resume(emptyList())
            }
        }
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

}