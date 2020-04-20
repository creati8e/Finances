package serg.chuprin.finances.feature.dashboard.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreFormattersProvider
import serg.chuprin.finances.core.api.di.provider.CoreManagerProvider
import serg.chuprin.finances.core.api.di.provider.CoreRepositoriesProvider
import serg.chuprin.finances.core.api.di.provider.CoreServicesProvider
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateFormatter
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface DashboardDependencies {
    val userRepository: UserRepository
    val transactionRepository: TransactionRepository

    val dateFormatter: DateFormatter
    val resourceManger: ResourceManger
    val amountFormatter: AmountFormatter
    val dataPeriodFormatter: DataPeriodFormatter

    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
}

@Component(
    dependencies = [
        CoreManagerProvider::class,
        CoreServicesProvider::class,
        CoreFormattersProvider::class,
        CoreRepositoriesProvider::class
    ]
)
internal interface DashboardDependenciesComponent : DashboardDependencies