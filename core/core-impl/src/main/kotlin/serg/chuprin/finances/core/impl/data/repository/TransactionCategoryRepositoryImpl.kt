package serg.chuprin.finances.core.impl.data.repository

import serg.chuprin.finances.core.api.domain.model.TransactionCategory
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class TransactionCategoryRepositoryImpl @Inject constructor() : TransactionCategoryRepository {

    override fun getPredefinedCategories(): List<TransactionCategory> {
        TODO("Not yet implemented")
    }

}