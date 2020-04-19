package serg.chuprin.finances.core.api.domain.repository

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
interface TransactionCategoryRepository {

    suspend fun createPredefinedCategories(userId: Id)

}