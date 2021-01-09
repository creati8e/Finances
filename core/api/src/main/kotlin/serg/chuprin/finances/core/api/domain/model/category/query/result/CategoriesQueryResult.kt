package serg.chuprin.finances.core.api.domain.model.category.query.result

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class CategoriesQueryResult(
    map: Map<Id, CategoryWithParent>
) : Map<Id, CategoryWithParent> by map {

    override fun equals(other: Any?): Boolean {
        return (other as? CategoriesQueryResult)?.entries == entries
    }

    override fun hashCode(): Int = entries.hashCode()

}