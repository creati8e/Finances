package serg.chuprin.finances.feature.moneyaccounts.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.feature.moneyaccounts.list.dependencies.MoneyAccountsListDependencies
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.viewmodel.MoneyAccountsListViewModel
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.MoneyAccountsListFragment
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
@ScreenScope
@Component(
    modules = [MoneyAccountsListModule::class],
    dependencies = [MoneyAccountsListDependencies::class]
)
interface MoneyAccountsListComponent :
    ViewModelComponent<MoneyAccountsListViewModel>,
    InjectableComponent<MoneyAccountsListFragment> {

    companion object {

        fun get(screenArguments: MoneyAccountsListScreenArguments): MoneyAccountsListComponent {
            return DaggerMoneyAccountsListComponent
                .factory()
                .newComponent(Injector.getMoneyAccountsListDependencies(), screenArguments)
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: MoneyAccountsListDependencies,
            @BindsInstance
            screenArguments: MoneyAccountsListScreenArguments
        ): MoneyAccountsListComponent

    }

}