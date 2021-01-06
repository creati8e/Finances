package serg.chuprin.finances.core.api.presentation.builder

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
interface TransactionCellBuilder {

    enum class DateTimeFormattingMode {
        ONLY_TIME, DATE_AND_TIME
    }

    fun build(
        transaction: Transaction,
        // Pass null if money account should not be displayed.
        moneyAccount: MoneyAccount?,
        categoryWithParent: TransactionCategoryWithParent?,
        dateTimeFormattingMode: DateTimeFormattingMode
    ): TransactionCell

}