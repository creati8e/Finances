package serg.chuprin.finances.core.api.domain.model

import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Suppress("DataClassPrivateConstructor")
data class Transaction(
    val id: Id,
    val date: Date,
    val ownerId: Id,
    val amount: String,
    val moneyAccountId: Id,
    val type: TransactionType,
    val currencyCode: String
) {

    companion object {

        fun create(
            id: String?,
            date: Date?,
            ownerId: String?,
            amount: String?,
            currencyCode: String?,
            type: TransactionType?,
            moneyAccountId: String?
        ): Transaction? {
            if (type == null) return null
            if (date == null) return null
            if (ownerId == null) return null
            if (id.isNullOrBlank()) return null
            if (moneyAccountId == null) return null
            if (amount.isNullOrBlank()) return null
            if (currencyCode.isNullOrEmpty()) return null
            return Transaction(
                type = type,
                date = date,
                amount = amount,
                id = Id.existing(id),
                currencyCode = currencyCode,
                ownerId = Id.existing(ownerId),
                moneyAccountId = Id.existing(moneyAccountId)
            )
        }

    }

    val amountBigDecimal: BigDecimal
        get() = BigDecimal(amount)

    val isExpense: Boolean
        get() = type == TransactionType.EXPENSE

    val isIncome: Boolean
        get() = type == TransactionType.INCOME

}