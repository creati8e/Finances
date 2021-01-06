package serg.chuprin.finances.feature.transactions.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
interface TransactionReportFilterRepository {

    val filterFlow: Flow<TransactionReportFilter>

    fun updateFilter(newFilter: TransactionReportFilter)

}