package serg.chuprin.finances.feature.categories.list.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.feature.categories.list.presentation.model.viewmodel.CategoriesListViewModel

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
            dependencies: CategoriesListDependencies
        ): CategoriesListComponent {
            return DaggerCategoriesListComponent
                .factory()
                .newComponent(dependencies, arguments)
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