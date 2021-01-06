package serg.chuprin.finances.feature.transaction.domain.model

import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
sealed class TransactionChosenOperation {

    data class Plain(
        val type: PlainTransactionType
    ) : TransactionChosenOperation()

    fun isPlain(): Boolean = this is Plain

}