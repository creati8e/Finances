package serg.chuprin.finances.app.di.feature

import serg.chuprin.finances.app.di.feature.dependencies.DaggerAuthorizationDependenciesComponent
import serg.chuprin.finances.app.di.feature.dependencies.DaggerCategoriesListDependenciesComponent
import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.authorization.presentation.di.AuthorizationDependencies
import serg.chuprin.finances.feature.categories.impl.presentation.di.CategoriesListDependencies

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
class FeatureDependenciesProviderImpl : FeatureDependenciesProvider {

    override val authorizationDependencies: AuthorizationDependencies
        get() {
            return DaggerAuthorizationDependenciesComponent
                .builder()
                .coreDependenciesProvider(CoreDependenciesComponent.get())
                .build()
        }
    override val categoriesListDependencies: CategoriesListDependencies
        get() {
            return DaggerCategoriesListDependenciesComponent
                .builder()
                .coreDependenciesProvider(CoreDependenciesComponent.get())
                .build()
        }

}