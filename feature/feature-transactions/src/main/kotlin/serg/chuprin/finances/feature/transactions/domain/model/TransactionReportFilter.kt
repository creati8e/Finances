package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.ReportDataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType

/**
 * Created by Sergey Chuprin on 11.12.2020.
 */
sealed class TransactionReportFilter(
    open val dataPeriod: ReportDataPeriod,
) {

    data class Plain(
        override val dataPeriod: ReportDataPeriod,
        override val transactionType: PlainTransactionType?
    ) : TransactionReportFilter(dataPeriod) {

        override val categoryIds: Set<Id>
            get() = emptySet()

    }

    data class SingleCategory(
        val categoryId: Id,
        override val dataPeriod: ReportDataPeriod,
        override val transactionType: PlainTransactionType
    ) : TransactionReportFilter(dataPeriod) {

        override val categoryIds: Set<Id>
            get() = setOf(categoryId)

    }

    data class Categories(
        override val categoryIds: Set<Id>,
        override val transactionType: PlainTransactionType,
        override val dataPeriod: ReportDataPeriod
    ) : TransactionReportFilter(dataPeriod)

    abstract val transactionType: PlainTransactionType?

    abstract val categoryIds: Set<Id>

    companion object {

        val UNINITIALIZED = Plain(
            transactionType = null,
            dataPeriod = ReportDataPeriod.AllTime
        )

    }

}