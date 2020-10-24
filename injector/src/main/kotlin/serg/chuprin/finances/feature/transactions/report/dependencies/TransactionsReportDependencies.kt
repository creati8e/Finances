package serg.chuprin.finances.feature.transactions.report.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
interface TransactionsReportDependencies {

    val userRepository: UserRepository

    val dateTimeFormatter: DateTimeFormatter
    val transactionCellBuilder: TransactionCellBuilder
    val transactionsByDayGrouper: TransactionsByDayGrouper
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface TransactionsReportDependenciesComponent : TransactionsReportDependencies