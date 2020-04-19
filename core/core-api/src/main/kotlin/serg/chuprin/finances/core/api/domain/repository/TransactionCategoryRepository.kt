package serg.chuprin.finances.core.api.domain.repository

import serg.chuprin.finances.core.api.domain.model.TransactionCategory

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
interface TransactionCategoryRepository {

    fun getPredefinedCategories(): List<TransactionCategory>

}