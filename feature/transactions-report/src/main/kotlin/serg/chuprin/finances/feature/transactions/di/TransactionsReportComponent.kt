package serg.chuprin.finances.feature.transactions.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
import serg.chuprin.finances.feature.transactions.domain.model.ReportDataPeriod
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.presentation.model.viewmodel.TransactionsReportViewModel
import serg.chuprin.finances.feature.transactions.presentation.view.TransactionsReportFragment

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
@[ScreenScope Component(
    modules = [TransactionsReportModule::class],
    dependencies = [TransactionsReportDependencies::class]
)]
interface TransactionsReportComponent :
    InjectableComponent<TransactionsReportFragment>,
    ViewModelComponent<TransactionsReportViewModel> {

    companion object {

        fun get(
            arguments: TransactionsReportScreenArguments,
            dependencies: TransactionsReportDependencies
        ): TransactionsReportComponent {
            val initialFilter = arguments.toFilter()
            return DaggerTransactionsReportComponent
                .factory()
                .newComponent(
                    dependencies,
                    initialFilter,
                    initialFilter.reportDataPeriod.dataPeriod
                )
        }

        private fun TransactionsReportScreenArguments.toFilter(): TransactionReportFilter {
            val reportDataPeriod = ReportDataPeriod.Predefined(dataPeriodUi.toDataPeriod())
            return when (this) {
                is TransactionsReportScreenArguments.Transactions -> {
                    TransactionReportFilter.Plain(
                        reportDataPeriod = reportDataPeriod,
                        transactionType = transactionType
                    )
                }
                is TransactionsReportScreenArguments.AllTransactions -> {
                    TransactionReportFilter.Plain(
                        transactionType = null,
                        reportDataPeriod = reportDataPeriod
                    )
                }
                is TransactionsReportScreenArguments.Category.Other -> {
                    TransactionReportFilter.Categories(
                        reportDataPeriod = reportDataPeriod,
                        transactionType = transactionType,
                        categoryIds = categoryIds.mapTo(mutableSetOf(), Id.Companion::existing)
                    )
                }
                is TransactionsReportScreenArguments.Category.Concrete -> {
                    TransactionReportFilter.SingleCategory(
                        categoryId = categoryId,
                        reportDataPeriod = reportDataPeriod,
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
            @BindsInstance initialFilter: TransactionReportFilter,
            @BindsInstance initialDataPeriod: DataPeriod?
        ): TransactionsReportComponent

    }

}