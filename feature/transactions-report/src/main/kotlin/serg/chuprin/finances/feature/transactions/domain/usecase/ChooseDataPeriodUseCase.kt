package serg.chuprin.finances.feature.transactions.domain.usecase

import serg.chuprin.finances.feature.transactions.domain.model.ReportDataPeriod
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class ChooseDataPeriodUseCase @Inject constructor(
    private val filterRepository: TransactionReportFilterRepository
) {

    fun execute(currentFilter: TransactionReportFilter, dataPeriod: ReportDataPeriod) {
        filterRepository.updateFilter(currentFilter.setDataPeriod(dataPeriod))
    }

}