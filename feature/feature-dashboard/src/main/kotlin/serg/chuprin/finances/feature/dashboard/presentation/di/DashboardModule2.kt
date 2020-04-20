package serg.chuprin.finances.feature.dashboard.presentation.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.dashboard.data.repository.DashboardDataPeriodRepositoryImpl
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardHeaderWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardRecentTransactionsWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardHeaderWidgetCellBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardRecentTransactionsWidgetCellBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardWidgetCellBuilder

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
@Module
interface DashboardModule2 {

    @[Binds ScreenScope]
    fun bindDashboardPeriodRepository(
        impl: DashboardDataPeriodRepositoryImpl
    ): DashboardDataPeriodRepository

    // region Widget builders.

    @[Binds ScreenScope IntoSet]
    fun bindDashboardHeaderWidgetBuilder(
        impl: DashboardHeaderWidgetBuilder
    ): DashboardWidgetBuilder<*>

    @[Binds ScreenScope IntoSet]
    fun bindDashboardRecentTransactionsWidgetBuilder(
        impl: DashboardRecentTransactionsWidgetBuilder
    ): DashboardWidgetBuilder<*>

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

    // endregion

}