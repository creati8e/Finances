package serg.chuprin.finances.core.api.extensions

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.Transaction
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
val Collection<Transaction>.categoryIds: List<Id>
    get() = mapNotNull(Transaction::categoryId).distinct()

val Collection<Transaction>.amount: BigDecimal
    get() {
        return fold(
            BigDecimal.ZERO,
            { balance, transaction ->
                balance + transaction.amount
            }
        )
    }