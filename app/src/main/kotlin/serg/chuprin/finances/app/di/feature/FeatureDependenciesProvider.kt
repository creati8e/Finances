package serg.chuprin.finances.app.di.feature

import serg.chuprin.finances.feature.authorization.presentation.di.AuthorizationDependenciesProvider
import serg.chuprin.finances.feature.categories.impl.presentation.di.CategoriesListDependenciesProvider
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardDependenciesProvider

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
interface FeatureDependenciesProvider :
    DashboardDependenciesProvider,
    AuthorizationDependenciesProvider,
    CategoriesListDependenciesProvider