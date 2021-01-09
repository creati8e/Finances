package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class TransactionToCategory(
    map: Map<Transaction, CategoryWithParent?>
) : Map<Transaction, CategoryWithParent?> by map {

    override fun hashCode(): Int = entries.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? TransactionToCategory)?.entries == entries
    }

}