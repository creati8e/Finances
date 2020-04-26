package serg.chuprin.finances.core.api.domain.model.transaction

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.extensions.toLocalDateTimeUTC
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Suppress("DataClassPrivateConstructor")
data class Transaction(
    val id: Id,
    val ownerId: Id,
    val moneyAccountId: Id,
    val type: TransactionType,
    val currencyCode: String,
    val categoryId: Id? = null,
    private val _date: Date = Date(),
    /**
     * String representation of [BigDecimal]; May start with "-" symbol.
     */
    private val _amount: String
) {

    companion object {

        fun create(
            id: String?,
            date: Date?,
            ownerId: String?,
            amount: String?,
            categoryId: String?,
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
                _date = date,
                _amount = amount,
                id = Id.existing(
                    id
                ),
                currencyCode = currencyCode,
                ownerId = Id.existing(
                    ownerId
                ),
                categoryId = categoryId?.let(::Id),
                moneyAccountId = Id.existing(
                    moneyAccountId
                )
            )
        }

    }

    val amount: BigDecimal
        get() = BigDecimal(_amount)

    val isExpense: Boolean
        get() = _amount.startsWith("-")

    val isIncome: Boolean
        get() = !isExpense

    val isBalance: Boolean
        get() = type == TransactionType.BALANCE

    val dateTime: LocalDateTime
        get() = _date.toLocalDateTimeUTC()

}