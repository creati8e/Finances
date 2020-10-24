package serg.chuprin.finances.core.impl.presentation.builder

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
internal class TransactionCellBuilderImpl @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter,
    private val categoryColorFormatter: CategoryColorFormatter,
    private val transactionCategoryWithParentFormatter: TransactionCategoryWithParentFormatter
) : TransactionCellBuilder {

    override fun build(
        transaction: Transaction,
        categoryWithParent: TransactionCategoryWithParent?
    ): TransactionCell {
        val (parentCategoryName, subcategoryName) =
            transactionCategoryWithParentFormatter.format(categoryWithParent, transaction)
        return TransactionCell(
            transaction = transaction,
            isIncome = transaction.isIncome,
            subcategoryName = subcategoryName,
            parentCategoryName = parentCategoryName,
            time = dateTimeFormatter.formatTime(transaction.dateTime),
            color = categoryColorFormatter.format(categoryWithParent?.category),
            amount = amountFormatter.format(
                round = false,
                amount = transaction.amount,
                currency = transaction.currency
            )
        )
    }

}