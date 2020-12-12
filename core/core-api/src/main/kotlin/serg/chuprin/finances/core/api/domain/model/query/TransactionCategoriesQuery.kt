package serg.chuprin.finances.core.api.domain.model.query

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionCategoriesQuery(
    val userId: Id?,
    val relation: Relation?,
    val categoryIds: Set<Id>,
    val type: TransactionCategoryType?
) {

    enum class Relation {

        /**
         * Indicates that children categories should be retrieved
         * for all categories from [categoryIds].
         */
        RETRIEVE_CHILDREN
    }

}