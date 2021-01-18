package serg.chuprin.finances.feature.moneyaccount.creation.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.viewmodel.MoneyAccountCreationViewModel
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.view.MoneyAccountCreationFragment

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@[ScreenScope Component(
    modules = [MoneyAccountCreationModule::class],
    dependencies = [MoneyAccountCreationDependencies::class]
)]
interface MoneyAccountCreationComponent :
    ViewModelComponent<MoneyAccountCreationViewModel>,
    InjectableComponent<MoneyAccountCreationFragment> {

    companion object {

        fun get(
            screenArguments: MoneyAccountScreenArguments,
            dependencies: MoneyAccountCreationDependencies
        ): MoneyAccountCreationComponent {
            return DaggerMoneyAccountCreationComponent
                .factory()
                .newComponent(dependencies, screenArguments)
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: MoneyAccountCreationDependencies,
            @BindsInstance
            screenArguments: MoneyAccountScreenArguments
        ): MoneyAccountCreationComponent

    }

}