package serg.chuprin.finances.feature.moneyaccounts.creation

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreFactory
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
interface MoneyAccountCreationDependencies {
    val amountParser: AmountParser
    val amountFormatter: AmountFormatter
    val userRepository: UserRepository
    val currencyRepository: CurrencyRepository
    val transactionRepository: TransactionRepository
    val moneyAccountRepository: MoneyAccountRepository
    val currencyChoiceStoreFactory: CurrencyChoiceStoreFactory
}

@Component(dependencies = [CoreDependenciesProvider::class])
interface MoneyAccountCreationDependenciesComponent : MoneyAccountCreationDependencies