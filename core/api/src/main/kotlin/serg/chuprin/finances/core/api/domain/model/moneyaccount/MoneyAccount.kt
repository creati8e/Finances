package serg.chuprin.finances.core.api.domain.model.moneyaccount

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import java.util.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
data class MoneyAccount(
    val id: Id,
    val name: String,
    val ownerId: Id,
    val isFavorite: Boolean,
    val currencyCode: String
) {

    companion object {

        val EMPTY = MoneyAccount(
            Id.UNKNOWN,
            EMPTY_STRING,
            Id.UNKNOWN,
            false,
            EMPTY_STRING
        )

        fun create(
            id: String?,
            name: String?,
            ownerId: String?,
            isFavorite: Boolean?,
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
                ownerId = Id.existing(ownerId),
                isFavorite = isFavorite ?: false
            )
        }

    }

    val currency: Currency
        get() = Currency.getInstance(currencyCode)

}