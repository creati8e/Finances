package serg.chuprin.finances.feature.moneyaccounts.di

import dagger.Component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.moneyaccounts.list.dependencies.MoneyAccountsListDependencies
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.viewmodel.MoneyAccountsListViewModel
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
@Component(dependencies = [MoneyAccountsListDependencies::class])
interface MoneyAccountsListComponent : ViewModelComponent<MoneyAccountsListViewModel> {

    companion object {

        fun get(): MoneyAccountsListComponent {
            return DaggerMoneyAccountsListComponent
                .builder()
                .moneyAccountsListDependencies(Injector.getMoneyAccountsListDependencies())
                .build()
        }

    }

}