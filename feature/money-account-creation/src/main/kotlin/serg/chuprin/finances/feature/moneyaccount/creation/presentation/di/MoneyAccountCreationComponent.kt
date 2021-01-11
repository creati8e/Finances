package serg.chuprin.finances.feature.moneyaccount.creation.presentation.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.viewmodel.MoneyAccountCreationViewModel
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.view.MoneyAccountCreationFragment
import serg.chuprin.finances.feature.moneyaccounts.creation.MoneyAccountCreationDependencies
import serg.chuprin.finances.injector.Injector

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

        fun get(): MoneyAccountCreationComponent {
            return DaggerMoneyAccountCreationComponent
                .builder()
                .moneyAccountCreationDependencies(Injector.getMoneyAccountCreationDependencies())
                .build()
        }

    }

}