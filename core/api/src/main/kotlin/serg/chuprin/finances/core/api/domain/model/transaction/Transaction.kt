package serg.chuprin.finances.core.api.domain.model.transaction

import serg.chuprin.finances.core.api.domain.model.Id
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 *
 * Transaction represents operations in money accounts: expense, income, balance correction etc.
 */
data class Transaction(
    val id: Id,
    val ownerId: Id,
    val moneyAccountId: Id,
    val type: TransactionType,
    val currencyCode: String,
    val categoryId: Id? = null,
    private val _dateTime: LocalDateTime,
    /**
     * String representation of [BigDecimal].
     * For expense transaction it starts with "-" symbol.
     * I.e "-12.5".
     */
    private val _amount: String
) {

    companion object {

        fun create(
            id: String?,
            dateTime: LocalDateTime?,
            ownerId: String?,
            amount: String?,
            categoryId: String?,
            currencyCode: String?,
            type: TransactionType?,
            moneyAccountId: String?
        ): Transaction? {
            if (type == null) return null
            if (dateTime == null) return null
            if (ownerId == null) return null
            if (id.isNullOrBlank()) return null
            if (moneyAccountId == null) return null
            if (amount.isNullOrBlank()) return null
            if (currencyCode.isNullOrEmpty()) return null
            return Transaction(
                type = type,
                _dateTime = dateTime,
                _amount = amount,
                id = Id.existing(id),
                currencyCode = currencyCode,
                ownerId = Id.existing(ownerId),
                categoryId = categoryId?.let(::Id),
                moneyAccountId = Id.existing(moneyAccountId)
            )
        }

    }

    val amount: BigDecimal
        get() = BigDecimal(_amount)

    val isExpense: Boolean
        get() = isPlain && _amount.startsWith("-")

    val isIncome: Boolean
        get() = isPlain && _amount.startsWith("-").not()

    val isBalance: Boolean
        get() = type == TransactionType.BALANCE

    val isPlain: Boolean
        get() = type == TransactionType.PLAIN

    val dateTime: LocalDateTime
        get() = _dateTime.withSecond(0).withNano(0)

    val currency: Currency
        get() = Currency.getInstance(currencyCode)

}