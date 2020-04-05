package serg.chuprin.finances.core.api.domain.model

import java.util.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Suppress("DataClassPrivateConstructor")
data class Transaction private constructor(
    val id: Id,
    val date: Date,
    val userId: Id,
    val amount: String
) {

    companion object {

        fun create(
            id: String?,
            date: Date?,
            userId: String?,
            amount: String?
        ): Transaction? {
            if (date == null) return null
            if (id.isNullOrBlank()) return null
            if (userId.isNullOrBlank()) return null
            if (amount.isNullOrBlank()) return null
            return Transaction(
                id = Id(id),
                date = date,
                amount = amount,
                userId = Id(userId)
            )
        }

    }

}