package serg.chuprin.finances.feature.transactions.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
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
                .builder()
                .transactionsReportDependencies(Injector.getTransactionsReportDependencies())
                .build()
        }

    }

}