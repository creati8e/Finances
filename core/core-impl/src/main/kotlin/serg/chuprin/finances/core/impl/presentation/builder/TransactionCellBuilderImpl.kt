package serg.chuprin.finances.core.impl.presentation.builder

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder.DateTimeFormattingMode
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
internal class TransactionCellBuilderImpl @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter,
    private val categoryColorFormatter: CategoryColorFormatter,
    private val categoryFormatter: TransactionCategoryWithParentFormatter
) : TransactionCellBuilder {

    override fun build(
        transaction: Transaction,
        categoryWithParent: TransactionCategoryWithParent?,
        dateTimeFormattingMode: DateTimeFormattingMode
    ): TransactionCell {
        val (parentCategoryName, subcategoryName) =
            categoryFormatter.format(categoryWithParent, transaction)
        return TransactionCell(
            transaction = transaction,
            isIncome = transaction.isIncome,
            subcategoryName = subcategoryName,
            parentCategoryName = parentCategoryName,
            time = transaction.dateTime.format(dateTimeFormattingMode),
            color = categoryColorFormatter.format(categoryWithParent?.category),
            amount = amountFormatter.format(
                round = false,
                withSign = true,
                withCurrencySymbol = true,
                amount = transaction.amount,
                currency = transaction.currency
            )
        )
    }

    private fun LocalDateTime.format(dateTimeFormattingMode: DateTimeFormattingMode): String {
        return when (dateTimeFormattingMode) {
            DateTimeFormattingMode.ONLY_TIME -> dateTimeFormatter.formatTime(this)
            DateTimeFormattingMode.DATE_AND_TIME -> dateTimeFormatter.formatForTransaction(this)
        }
    }

}