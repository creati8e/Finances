package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import serg.chuprin.finances.core.api.domain.model.PredefinedTransactionCategories
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.impl.data.datasource.assets.PredefinedTransactionCategoriesDataSource
import serg.chuprin.finances.core.impl.data.mapper.PredefinedTransactionCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class TransactionCategoryRepositoryImpl @Inject constructor(
    private val predefinedCategoryMapper: PredefinedTransactionCategoryMapper,
    private val predefinedCategoriesDataSource: PredefinedTransactionCategoriesDataSource
) : TransactionCategoryRepository {

    override suspend fun getPredefinedCategories(): PredefinedTransactionCategories {
        return withContext(Dispatchers.IO) {
            val incomeCategories = async {
                predefinedCategoriesDataSource
                    .getIncomeCategories()
                    .mapNotNull(predefinedCategoryMapper)
            }
            val expenseCategories = async {
                predefinedCategoriesDataSource
                    .getExpenseCategories()
                    .mapNotNull(predefinedCategoryMapper)
            }
            PredefinedTransactionCategories(
                incomeCategories = incomeCategories.await(),
                expenseCategories = expenseCategories.await()
            )
        }
    }

}