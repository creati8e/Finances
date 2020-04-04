package serg.chuprin.finances.feature.dashboard.presentation.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.dashboard.dependencies.DashboardDependencies

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@ScreenScope
@Component(dependencies = [DashboardDependencies::class])
interface DashboardComponent