package serg.chuprin.finances.feature.dashboard.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.dashboard.data.repository.DashboardDataPeriodRepositoryImpl
import serg.chuprin.finances.feature.dashboard.domain.builder.*
import serg.chuprin.finances.feature.dashboard.domain.builder.categories.DashboardCategoriesWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.*
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardStore
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardStoreFactory

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
@Module
interface DashboardModule {

    companion object {

        @[Provides ScreenScope]
        fun provideStore(factory: DashboardStoreFactory): DashboardStore = factory.create()

    }

    @[Binds ScreenScope]
    fun bindDashboardPeriodRepository(
        impl: DashboardDataPeriodRepositoryImpl
    ): DashboardDataPeriodRepository

    // region Widget builders.

    @[Binds ScreenScope IntoSet]
    fun bindDashboardBalanceWidgetBuilder(
        impl: DashboardBalanceWidgetBuilder
    ): DashboardWidgetBuilder<*>

    @[Binds ScreenScope IntoSet]
    fun bindDashboardRecentTransactionsWidgetBuilder(
        impl: DashboardRecentTransactionsWidgetBuilder
    ): DashboardWidgetBuilder<*>

    @[Binds ScreenScope IntoSet]
    fun bindDashboardMoneyAccountsWidgetBuilder(
        impl: DashboardMoneyAccountsWidgetBuilder
    ): DashboardWidgetBuilder<*>

    @[Binds ScreenScope IntoSet]
    fun bindsDashboardPeriodSelectorWidgetBuilder(
        impl: DashboardPeriodSelectorWidgetBuilder
    ): DashboardWidgetBuilder<*>

    @[Binds ScreenScope IntoSet]
    fun bindDashboardCategoriesWidgetBuilder(
        impl: DashboardCategoriesWidgetBuilder
    ): DashboardWidgetBuilder<*>

    // endregion


    // region Widget cell builders.

    @[Binds ScreenScope IntoSet]
    fun bindDashboardBalanceWidgetCellBuilder(
        impl: DashboardBalanceWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    @[Binds ScreenScope IntoSet]
    fun bindsDashboardPeriodSelectorWidgetCellBuilder(
        impl: DashboardPeriodSelectorWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    @[Binds ScreenScope IntoSet]
    fun bindDashboardRecentTransactionsWidgetCellBuilder(
        impl: DashboardRecentTransactionsWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    @[Binds ScreenScope IntoSet]
    fun bindDashboardMoneyAccountsWidgetCellBuilder(
        impl: DashboardMoneyAccountsWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    @[Binds ScreenScope IntoSet]
    fun bindDashboardCategoriesWidgetCellBuilder(
        impl: DashboardCategoriesWidgetCellBuilder
    ): DashboardWidgetCellBuilder

    // endregion

}