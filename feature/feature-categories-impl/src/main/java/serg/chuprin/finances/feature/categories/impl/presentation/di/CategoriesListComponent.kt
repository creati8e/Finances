package serg.chuprin.finances.feature.categories.impl.presentation.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.feature.categories.CategoriesListDependencies
import serg.chuprin.finances.feature.categories.impl.presentation.model.viewmodel.CategoriesListViewModel
import serg.chuprin.finances.injector.Injector

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

        fun get(arguments: CategoriesListScreenArguments): CategoriesListComponent {
            return DaggerCategoriesListComponent
                .factory()
                .newComponent(Injector.getCategoriesListDependencies(), arguments)
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