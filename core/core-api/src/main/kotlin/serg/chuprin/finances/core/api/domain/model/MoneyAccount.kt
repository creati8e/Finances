package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
data class MoneyAccount(
    val id: Id,
    val name: String,
    val ownerId: Id,
    val currencyCode: String
) {

    companion object {

        fun create(
            id: String?,
            name: String?,
            ownerId: String?,
            currencyCode: String?
        ): MoneyAccount? {
            if (id.isNullOrEmpty()) return null
            if (name.isNullOrEmpty()) return null
            if (ownerId.isNullOrEmpty()) return null
            if (currencyCode.isNullOrEmpty()) return null
            return MoneyAccount(
                name = name,
                id = Id.existing(id),
                currencyCode = currencyCode,
                ownerId = Id.existing(ownerId)
            )
        }

    }

}