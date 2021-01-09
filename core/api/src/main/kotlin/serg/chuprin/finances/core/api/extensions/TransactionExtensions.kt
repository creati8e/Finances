package serg.chuprin.finances.core.api.extensions

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
val Collection<Transaction>.categoryIds: Set<Id>
    get() = mapNotNullTo(mutableSetOf(), Transaction::categoryId)

val Collection<Transaction>.amount: BigDecimal
    get() {
        return fold(
            BigDecimal.ZERO,
            { balance, transaction ->
                balance + transaction.amount
            }
        )
    }

val Map<Transaction, *>.amount: BigDecimal
    get() {
        return entries.fold(BigDecimal.ZERO, { balance, (transaction) ->
            balance + transaction.amount
        })
    }