package serg.chuprin.finances.feature.moneyaccount.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.feature.moneyaccount.presentation.model.viewmodel.MoneyAccountViewModel
import serg.chuprin.finances.feature.moneyaccount.presentation.view.MoneyAccountFragment

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@[ScreenScope Component(
    modules = [MoneyAccountModule::class],
    dependencies = [MoneyAccountDependencies::class]
)]
interface MoneyAccountComponent :
    ViewModelComponent<MoneyAccountViewModel>,
    InjectableComponent<MoneyAccountFragment> {

    companion object {

        fun get(
            screenArguments: MoneyAccountScreenArguments,
            dependencies: MoneyAccountDependencies
        ): MoneyAccountComponent {
            return DaggerMoneyAccountComponent
                .factory()
                .newComponent(dependencies, screenArguments)
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: MoneyAccountDependencies,
            @BindsInstance
            screenArguments: MoneyAccountScreenArguments
        ): MoneyAccountComponent

    }

}