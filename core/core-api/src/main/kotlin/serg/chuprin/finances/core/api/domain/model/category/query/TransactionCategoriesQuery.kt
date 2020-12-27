package serg.chuprin.finances.core.api.domain.model.category.query

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionCategoriesQuery(
    val userId: Id,
    val relation: Relation? = null,
    val categoryIds: Set<Id> = emptySet(),
    val type: TransactionCategoryType? = null
) {

    enum class Relation {

        /**
         * Indicates that children categories should be retrieved
         * for all categories from [categoryIds].
         */
        RETRIEVE_CHILDREN,

        /**
         * Indicates that parent categories should be retrieved
         * for all categories from [categoryIds].
         */
        RETRIEVE_PARENTS
    }

}