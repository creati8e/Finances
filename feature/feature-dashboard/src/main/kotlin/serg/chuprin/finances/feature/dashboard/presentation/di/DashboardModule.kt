package serg.chuprin.finances.feature.dashboard.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.dashboard.data.repository.DashboardDataPeriodRepositoryImpl
import serg.chuprin.finances.feature.dashboard.data.repository.DashboardTransactionCategoriesTypeRepositoryImpl
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardHeaderWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardMoneyAccountsWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardRecentTransactionsWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardTransactionCategoriesTypeRepository
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardHeaderWidgetCellBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardMoneyAccountsWidgetCellBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardRecentTransactionsWidgetCellBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardWidgetCellBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.store.*

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
@Module
interface DashboardModule {

    companion object {

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

    @[Binds ScreenScope]
    fun bindDashboardPeriodRepository(
        impl: DashboardDataPeriodRepositoryImpl
    ): DashboardDataPeriodRepository

    @[Binds ScreenScope]
    fun bindDashboardTransactionCategoriesTypeRepository(
        impl: DashboardTransactionCategoriesTypeRepositoryImpl
    ): DashboardTransactionCategoriesTypeRepository

    // region Widget builders.

    @[Binds ScreenScope IntoSet]
    fun bindDashboardHeaderWidgetBuilder(
        impl: DashboardHeaderWidgetBuilder
    ): DashboardWidgetBuilder<*>

    @[Binds ScreenScope IntoSet]
    fun bindDashboardRecentTransactionsWidgetBuilder(
        impl: DashboardRecentTransactionsWidgetBuilder
    ): DashboardWidgetBuilder<*>

    @[Binds ScreenScope IntoSet]
    fun bindDashboardMoneyAccountsWidgetBuilder(
        impl: DashboardMoneyAccountsWidgetBuilder
    ): DashboardWidgetBuilder<*>

//    @[Binds ScreenScope IntoSet]
//    fun bindDashboardCategoriesWidgetBuilder(
//        impl: DashboardCategoriesWidgetBuilder
//    ): DashboardWidgetBuilder<*>

    // endregion


    // region Widget cell builders.

    @[Binds ScreenScope IntoSet]
    fun bindDashboardHeaderWidgetCellBuilder(
        impl: DashboardHeaderWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    @[Binds ScreenScope IntoSet]
    fun bindDashboardRecentTransactionsWidgetCellBuilder(
        impl: DashboardRecentTransactionsWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    @[Binds ScreenScope IntoSet]
    fun bindDashboardMoneyAccountsWidgetCellBuilder(
        impl: DashboardMoneyAccountsWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    // endregion

}