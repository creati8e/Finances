package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.coroutineScope
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.impl.data.datasource.assets.PredefinedTransactionCategoriesDataSource
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.FirebaseTransactionCategoryDataSource
import serg.chuprin.finances.core.impl.data.mapper.PredefinedTransactionCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class TransactionCategoryRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseTransactionCategoryDataSource,
    private val predefinedCategoryMapper: PredefinedTransactionCategoryMapper,
    private val predefinedCategoriesDataSource: PredefinedTransactionCategoriesDataSource
) : TransactionCategoryRepository {

    override suspend fun createPredefinedCategories() {
        coroutineScope {
            val allCategories = predefinedCategoriesDataSource.getCategories().run {
                (expenseCategories + incomeCategories).mapNotNull(predefinedCategoryMapper)
            }
            firebaseDataSource.createTransactions(allCategories)
        }
    }

}