package serg.chuprin.finances.feature.transactions.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.transactions.presentation.TransactionReportNavigation

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
interface TransactionsReportDependencies : FeatureDependencies {

    val transactionReportNavigation: TransactionReportNavigation

    val userRepository: UserRepository
    val categoryRepository: CategoryRepository
    val transactionRepository: TransactionRepository
    val moneyAccountRepository: MoneyAccountRepository

    val amountFormatter: AmountFormatter
    val dateTimeFormatter: DateTimeFormatter
    val categoryColorFormatter: CategoryColorFormatter

    val resourceManger: ResourceManger
    val transactionCellBuilder: TransactionCellBuilder
    val transactionsByDayGrouper: TransactionsByDayGrouper
    val transactionAmountCalculator: TransactionAmountCalculator
    val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
}