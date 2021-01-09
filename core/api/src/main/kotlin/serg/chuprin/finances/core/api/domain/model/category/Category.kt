package serg.chuprin.finances.core.api.domain.model.category

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
data class Category(
    val id: Id,
    val ownerId: Id,
    val name: String,
    val colorHex: String,
    val parentCategoryId: Id? = null,
    val type: CategoryType
) {

    companion object {

        fun create(
            id: String?,
            name: String?,
            ownerId: String?,
            colorHex: String?,
            parentCategoryId: String?,
            type: CategoryType?
        ): Category? {
            if (type == null) return null
            if (id.isNullOrEmpty()) return null
            if (name.isNullOrEmpty()) return null
            if (ownerId.isNullOrEmpty()) return null
            if (colorHex.isNullOrEmpty()) return null
            return Category(
                name = name,
                type = type,
                colorHex = colorHex,
                id = Id.existing(id),
                ownerId = Id.existing(ownerId),
                parentCategoryId = parentCategoryId?.let(::Id)
            )
        }

    }

}