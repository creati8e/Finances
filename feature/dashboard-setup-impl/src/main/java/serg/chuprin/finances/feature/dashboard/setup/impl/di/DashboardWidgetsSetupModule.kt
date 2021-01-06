package serg.chuprin.finances.feature.dashboard.setup.impl.di

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository
import serg.chuprin.finances.feature.dashboard.setup.impl.data.repository.DashboardWidgetsRepositoryImpl

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
@Module
internal interface DashboardWidgetsSetupModule {

    @Binds
    fun bindsDashboardWidgetsRepository(
        impl: DashboardWidgetsRepositoryImpl
    ): DashboardWidgetsRepository

}