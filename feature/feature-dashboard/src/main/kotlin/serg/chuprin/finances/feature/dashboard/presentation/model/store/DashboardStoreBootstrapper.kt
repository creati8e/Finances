package serg.chuprin.finances.feature.dashboard.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import serg.chuprin.finances.feature.dashboard.domain.usecase.BuildDashboardUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardStoreBootstrapper @Inject constructor(
    private val userRepository: UserRepository,
    private val buildDashboardUseCase: BuildDashboardUseCase,
    private val dataPeriodRepository: DashboardDataPeriodRepository
) : StoreBootstrapper<DashboardAction> {

    override fun invoke(): Flow<DashboardAction> {
        return buildDashboardUseCase
            .execute()
            .map(DashboardAction::FormatDashboard)
            .onStart {
                val currentUser = userRepository.getCurrentUser()
                dataPeriodRepository.setCurrentDataPeriod(
                    DataPeriod.from(currentUser.dataPeriodType)
                )
            }
    }

}