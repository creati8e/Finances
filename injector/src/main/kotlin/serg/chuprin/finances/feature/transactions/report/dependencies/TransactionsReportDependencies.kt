package serg.chuprin.finances.feature.transactions.report.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
interface TransactionsReportDependencies {
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface TransactionsReportDependenciesComponent : TransactionsReportDependencies