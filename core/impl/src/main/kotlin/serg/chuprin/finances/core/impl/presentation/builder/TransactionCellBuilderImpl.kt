package serg.chuprin.finances.core.impl.presentation.builder

import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder.DateTimeFormattingMode
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
internal class TransactionCellBuilderImpl @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter,
    private val transitionNameBuilder: TransitionNameBuilder,
    private val categoryColorFormatter: CategoryColorFormatter,
    private val categoryNameFormatter: CategoryWithParentFormatter
) : TransactionCellBuilder {

    override fun build(
        transaction: Transaction,
        moneyAccount: MoneyAccount?,
        categoryWithParent: CategoryWithParent?,
        dateTimeFormattingMode: DateTimeFormattingMode
    ): TransactionCell {
        return TransactionCell(
            transaction = transaction,
            amount = formatAmount(transaction),
            moneyAccount = moneyAccount?.name.orEmpty(),
            time = transaction.dateTime.format(dateTimeFormattingMode),
            color = categoryColorFormatter.format(categoryWithParent?.category),
            categoryName = categoryNameFormatter.format(categoryWithParent, transaction),
            transitionName = transitionNameBuilder.buildForTransaction(transaction.id)
        )
    }

    private fun formatAmount(transaction: Transaction): String {
        return amountFormatter.format(
            round = false,
            withSign = true,
            withCurrencySymbol = true,
            amount = transaction.amount,
            currency = transaction.currency
        )
    }

    private fun LocalDateTime.format(dateTimeFormattingMode: DateTimeFormattingMode): String {
        return when (dateTimeFormattingMode) {
            DateTimeFormattingMode.ONLY_TIME -> dateTimeFormatter.formatTime(this)
            DateTimeFormattingMode.DATE_AND_TIME -> dateTimeFormatter.formatForTransaction(this)
        }
    }

}