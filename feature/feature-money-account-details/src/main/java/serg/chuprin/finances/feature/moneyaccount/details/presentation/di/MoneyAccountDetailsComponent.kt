package serg.chuprin.finances.feature.moneyaccount.details.presentation.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.viewmodel.MoneyAccountDetailsViewModel
import serg.chuprin.finances.feature.moneyaccounts.details.dependencies.MoneyAccountDetailsDependencies
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
@ScreenScope
@Component(dependencies = [MoneyAccountDetailsDependencies::class])
interface MoneyAccountDetailsComponent : ViewModelComponent<MoneyAccountDetailsViewModel> {

    companion object {

        fun get(): MoneyAccountDetailsComponent {
            return DaggerMoneyAccountDetailsComponent
                .builder()
                .moneyAccountDetailsDependencies(Injector.getMoneyAccountDetailsDependencies())
                .build()
        }

    }

}