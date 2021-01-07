package serg.chuprin.finances.feature.transactions.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.transactions.domain.model.ReportDataPeriod
import serg.chuprin.finances.feature.transactions.domain.usecase.ChooseDataPeriodUseCase
import serg.chuprin.finances.feature.transactions.presentation.model.builder.TransactionReportCellsBuilder
import serg.chuprin.finances.feature.transactions.presentation.model.builder.TransactionReportHeaderBuilder
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportActionExecutor @Inject constructor(
    private val cellsBuilder: TransactionReportCellsBuilder,
    private val headerBuilder: TransactionReportHeaderBuilder,
    private val chooseDataPeriodUseCase: ChooseDataPeriodUseCase
) : StoreActionExecutor<TransactionsReportAction, TransactionsReportState, TransactionsReportEffect, TransactionsReportEvent> {

    override fun invoke(
        action: TransactionsReportAction,
        state: TransactionsReportState,
        eventConsumer: Consumer<TransactionsReportEvent>,
        actionsFlow: Flow<TransactionsReportAction>
    ): Flow<TransactionsReportEffect> {
        return when (action) {
            is TransactionsReportAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    is TransactionsReportIntent.ClickOnDataChartCell -> {
                        handleClickOnDataChartCellIntent(intent, state)
                    }
                    is TransactionsReportIntent.ClickOnTransactionCell -> {
                        handleClickOnTransactionCellIntent(intent, eventConsumer)
                    }
                }
            }
            is TransactionsReportAction.FormatReport -> {
                handleFormatReportAction(action)
            }
        }
    }

    private fun handleClickOnTransactionCellIntent(
        intent: TransactionsReportIntent.ClickOnTransactionCell,
        eventConsumer: Consumer<TransactionsReportEvent>
    ): Flow<TransactionsReportEffect> {
        return emptyFlowAction {
            val screenArguments = TransactionScreenArguments.Editing(
                intent.transactionCell.transaction.id,
                intent.transactionCell.transitionName
            )
            eventConsumer(TransactionsReportEvent.NavigateToTransactionScreen(screenArguments))
        }
    }

    private fun handleClickOnDataChartCellIntent(
        intent: TransactionsReportIntent.ClickOnDataChartCell,
        state: TransactionsReportState
    ): Flow<TransactionsReportEffect> {
        return emptyFlowAction {
            val reportDataPeriod = when (val dataPeriod = state.filter.reportDataPeriod) {
                is ReportDataPeriod.Predefined -> {
                    dataPeriod.copy(dataPeriod = intent.dataPeriodAmountChartCell.dataPeriod)
                }
                is ReportDataPeriod.Custom, is ReportDataPeriod.AllTime -> {
                    throw IllegalStateException("Data period can not be chosen for")
                }
            }
            chooseDataPeriodUseCase.execute(state.filter, reportDataPeriod)
        }
    }

    private fun handleFormatReportAction(
        action: TransactionsReportAction.FormatReport
    ): Flow<TransactionsReportEffect> {
        return flowOfSingleValue {
            TransactionsReportEffect.ReportBuilt(
                filter = action.report.filter,
                header = headerBuilder.build(action.report),
                listCells = cellsBuilder.build(action.report)
            )
        }
    }

}