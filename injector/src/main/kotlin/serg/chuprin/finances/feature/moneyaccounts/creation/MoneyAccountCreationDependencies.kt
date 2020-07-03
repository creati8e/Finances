package serg.chuprin.finances.feature.moneyaccounts.creation

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreFactory

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
interface MoneyAccountCreationDependencies {
    val currencyChoiceStoreFactory: CurrencyChoiceStoreFactory
}

@Component(dependencies = [CoreDependenciesProvider::class])
interface MoneyAccountCreationDependenciesComponent : MoneyAccountCreationDependencies