package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
data class TransactionCategory(
    val id: Id,
    val name: String,
    val parentCategoryId: Id? = null,
    val type: TransactionCategoryType
) {

    companion object {

        fun create(
            id: String?,
            name: String?,
            parentCategoryId: String?,
            type: TransactionCategoryType?
        ): TransactionCategory? {
            if (type == null) return null
            if (id.isNullOrEmpty()) return null
            if (name.isNullOrEmpty()) return null
            return TransactionCategory(
                name = name,
                type = type,
                id = Id.existing(id),
                parentCategoryId = parentCategoryId?.let(::Id)
            )
        }

    }

}