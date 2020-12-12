package serg.chuprin.finances.feature.transactions.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.ReportDataPeriod
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.presentation.model.viewmodel.TransactionsReportViewModel
import serg.chuprin.finances.feature.transactions.report.dependencies.TransactionsReportDependencies
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
@[ScreenScope Component(
    modules = [TransactionsReportModule::class],
    dependencies = [TransactionsReportDependencies::class]
)]
interface TransactionsReportComponent : ViewModelComponent<TransactionsReportViewModel> {

    companion object {

        fun get(arguments: TransactionsReportScreenArguments): TransactionsReportComponent {
            return DaggerTransactionsReportComponent
                .factory()
                .newComponent(
                    Injector.getTransactionsReportDependencies(),
                    arguments.toFilter()
                )
        }

        private fun TransactionsReportScreenArguments.toFilter(): TransactionReportFilter {
            val reportDataPeriod = ReportDataPeriod.Predefined(dataPeriodUi.toDataPeriod())
            return when (this) {
                is TransactionsReportScreenArguments.Transactions -> {
                    TransactionReportFilter.Plain(
                        dataPeriod = reportDataPeriod,
                        transactionType = transactionType
                    )
                }
                is TransactionsReportScreenArguments.AllTransactions -> {
                    TransactionReportFilter.Plain(
                        transactionType = null,
                        dataPeriod = reportDataPeriod
                    )
                }
                is TransactionsReportScreenArguments.Category.Other -> {
                    TransactionReportFilter.Categories(
                        dataPeriod = reportDataPeriod,
                        transactionType = transactionType,
                        categoryIds = categoryIds.mapTo(mutableSetOf(), Id.Companion::existing)
                    )
                }
                is TransactionsReportScreenArguments.Category.Concrete -> {
                    TransactionReportFilter.SingleCategory(
                        categoryId = categoryId,
                        dataPeriod = reportDataPeriod,
                        transactionType = transactionType
                    )
                }
            }
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: TransactionsReportDependencies,
            @BindsInstance initialFilter: TransactionReportFilter
        ): TransactionsReportComponent

    }

}