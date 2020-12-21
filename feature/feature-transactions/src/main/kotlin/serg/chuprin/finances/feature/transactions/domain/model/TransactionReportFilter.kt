package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType

/**
 * Created by Sergey Chuprin on 11.12.2020.
 */
sealed class TransactionReportFilter(
    open val reportDataPeriod: ReportDataPeriod,
) {

    data class Plain(
        override val reportDataPeriod: ReportDataPeriod,
        override val transactionType: PlainTransactionType?
    ) : TransactionReportFilter(reportDataPeriod) {

        override val categoryIds: Set<Id>
            get() = emptySet()

    }

    data class SingleCategory(
        val categoryId: Id,
        override val reportDataPeriod: ReportDataPeriod,
        override val transactionType: PlainTransactionType
    ) : TransactionReportFilter(reportDataPeriod) {

        override val categoryIds: Set<Id>
            get() = setOf(categoryId)

    }

    data class Categories(
        override val categoryIds: Set<Id>,
        override val transactionType: PlainTransactionType,
        override val reportDataPeriod: ReportDataPeriod
    ) : TransactionReportFilter(reportDataPeriod)

    abstract val transactionType: PlainTransactionType?

    abstract val categoryIds: Set<Id>

    companion object {

        val UNINITIALIZED = Plain(
            transactionType = null,
            reportDataPeriod = ReportDataPeriod.AllTime
        )

    }

}