package serg.chuprin.finances.feature.dashboard.presentation.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.dashboard.presentation.model.store.*

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
@Module
object DashboardModule {

    @[Provides ScreenScope]
    fun provideStore(
        executor: DashboardActionExecutor,
        bootstrapper: DashboardStoreBootstrapper
    ): DashboardStore {
        return BaseStateStore(
            executor = executor,
            bootstrapper = bootstrapper,
            initialState = DashboardState(),
            reducer = DashboardStateReducer(),
            intentToActionMapper = DashboardIntentToActionMapper()
        )
    }

}