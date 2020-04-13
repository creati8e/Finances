package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
enum class TransactionType {
    INCOME,
    EXPENSE,

    /**
     * This type of transaction is not like real transaction.
     * It used only for setting current balance.
     */
    BALANCE
}