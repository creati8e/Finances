package serg.chuprin.finances.core.api.domain.model.category

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class CategoryIdToCategory(
    map: Map<Id, CategoryWithParent>
) : Map<Id, CategoryWithParent> by map {

    override fun equals(other: Any?): Boolean {
        return (other as? CategoryIdToCategory)?.entries == entries
    }

    override fun hashCode(): Int = entries.hashCode()

}