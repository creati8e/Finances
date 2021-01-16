package serg.chuprin.finances.feature.dashboard.setup.impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository
import serg.chuprin.finances.feature.dashboard.setup.impl.data.repository.DashboardWidgetsRepositoryImpl
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupStore
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupStoreFactory

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
@Module
internal interface DashboardWidgetsSetupModule {

    companion object {

        @[Provides ScreenScope]
        fun provideStore(factory: DashboardWidgetsSetupStoreFactory): DashboardWidgetsSetupStore {
            return factory.create()
        }

    }

    /**
     * Bind unscoped repository for API.
     */
    @Binds
    fun bindsDashboardWidgetsRepository(
        impl: DashboardWidgetsRepositoryImpl
    ): DashboardWidgetsRepository

    /**
     * Bind screen scoped repository for usage on screen.
     */
    @[Binds ScreenScope ScreenScoped]
    fun bindsDashboardWidgetsRepositoryApi(
        impl: DashboardWidgetsRepositoryImpl
    ): DashboardWidgetsRepository


}