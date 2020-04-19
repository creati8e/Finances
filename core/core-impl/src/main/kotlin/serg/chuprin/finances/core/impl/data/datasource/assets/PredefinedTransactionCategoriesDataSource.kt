package serg.chuprin.finances.core.impl.data.datasource.assets

import android.content.Context
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
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
                continuation.resume(Json.parse(deserializer, json))
            } catch (throwable: Throwable) {
                continuation.resume(emptyList())
            }
        }
    }

}