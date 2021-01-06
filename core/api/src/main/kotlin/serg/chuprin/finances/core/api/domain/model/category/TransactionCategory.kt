package serg.chuprin.finances.core.api.domain.model.category

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
data class TransactionCategory(
    val id: Id,
    val ownerId: Id,
    val name: String,
    val colorHex: String,
    val parentCategoryId: Id? = null,
    val type: TransactionCategoryType
) {

    companion object {

        fun create(
            id: String?,
            name: String?,
            ownerId: String?,
            colorHex: String?,
            parentCategoryId: String?,
            type: TransactionCategoryType?
        ): TransactionCategory? {
            if (type == null) return null
            if (id.isNullOrEmpty()) return null
            if (name.isNullOrEmpty()) return null
            if (ownerId.isNullOrEmpty()) return null
            if (colorHex.isNullOrEmpty()) return null
            return TransactionCategory(
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