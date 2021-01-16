package serg.chuprin.finances.feature.categories.impl.presentation.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.feature.categories.impl.presentation.model.viewmodel.CategoriesListViewModel

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
@Component(
    modules = [CategoriesListModule::class],
    dependencies = [CategoriesListDependencies::class]
)
interface CategoriesListComponent : ViewModelComponent<CategoriesListViewModel> {

    companion object {

        fun get(
            arguments: CategoriesListScreenArguments,
            application: Application
        ): CategoriesListComponent {
            return DaggerCategoriesListComponent
                .factory()
                .newComponent(dependencies(application), arguments)
        }

        private fun dependencies(application: Application): CategoriesListDependencies {
            return (application as CategoriesListDependenciesProvider).categoriesListDependencies
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: CategoriesListDependencies,
            @BindsInstance arguments: CategoriesListScreenArguments
        ): CategoriesListComponent

    }

}