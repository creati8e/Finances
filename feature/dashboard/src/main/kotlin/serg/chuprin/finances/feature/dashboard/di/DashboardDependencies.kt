package serg.chuprin.finances.feature.dashboard.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.AppDebugMenu
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.dashboard.presentation.DashboardNavigation
import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface DashboardDependencies : FeatureDependencies {

    val appDebugMenu: AppDebugMenu
    val dashboardNavigation: DashboardNavigation

    val userRepository: UserRepository
    val transactionRepository: TransactionRepository
    val moneyAccountRepository: MoneyAccountRepository
    val categoryRepository: CategoryRepository
    val dashboardWidgetsRepository: DashboardWidgetsRepository
    val transactionWithCategoriesLinker: TransactionWithCategoriesLinker

    val resourceManger: ResourceManger
    val amountFormatter: AmountFormatter
    val dataPeriodFormatter: DataPeriodFormatter
    val categoryColorFormatter: CategoryColorFormatter

    val transitionNameBuilder: TransitionNameBuilder
    val transactionCellBuilder: TransactionCellBuilder
    val transactionAmountCalculator: TransactionAmountCalculator
    val dataPeriodTypePopupMenuCellsBuilder: DataPeriodTypePopupMenuCellsBuilder

    val moneyAccountBalanceService: MoneyAccountBalanceService
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
}