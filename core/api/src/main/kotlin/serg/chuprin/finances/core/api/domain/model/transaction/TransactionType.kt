package serg.chuprin.finances.core.api.domain.model.transaction

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
enum class TransactionType {
    /**
     * Plain transaction; Could be income or expense.
     */
    PLAIN,

    /**
     * This type of transaction is not like real transaction.
     * It used only for setting current balance.
     */
    BALANCE
}