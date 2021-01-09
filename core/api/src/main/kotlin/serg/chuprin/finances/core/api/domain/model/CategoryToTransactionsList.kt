package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
class CategoryToTransactionsList(
    map: Map<Category?, List<Transaction>> = emptyMap()
) : Map<Category?, List<Transaction>> by map {

    override fun hashCode(): Int = entries.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? CategoryToTransactionsList)?.entries == entries
    }

}