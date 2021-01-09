package serg.chuprin.finances.core.api.domain.model.category.query

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryType

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class CategoriesQuery(
    val ownerId: Id,
    val relation: Relation? = null,
    val categoryIds: Set<Id> = emptySet(),
    val searchByName: String? = null,
    val type: CategoryType? = null
) {

    /**
     * Represents a 'relation' between parent and children categories or vice versa.
     * Relation is applied to provided [categoryIds].
     */
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