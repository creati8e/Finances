package serg.chuprin.finances.feature.dashboard.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateFormatter
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface DashboardDependencies {
    val userRepository: UserRepository
    val transactionRepository: TransactionRepository
    val moneyAccountRepository: MoneyAccountRepository

    val dateFormatter: DateFormatter
    val resourceManger: ResourceManger
    val amountFormatter: AmountFormatter
    val dataPeriodFormatter: DataPeriodFormatter

    val dataPeriodTypePopupMenuCellsBuilder: DataPeriodTypePopupMenuCellsBuilder

    val moneyAccountService: MoneyAccountService
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface DashboardDependenciesComponent : DashboardDependencies