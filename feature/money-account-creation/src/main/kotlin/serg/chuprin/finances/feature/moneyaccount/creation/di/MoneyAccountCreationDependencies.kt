package serg.chuprin.finances.feature.moneyaccount.creation.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.repository.*
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreProvider
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
interface MoneyAccountCreationDependencies : FeatureDependencies {
    val amountParser: AmountParser
    val amountFormatter: AmountFormatter

    val userRepository: UserRepository
    val categoryRepository: CategoryRepository
    val currencyRepository: CurrencyRepository
    val transactionRepository: TransactionRepository
    val moneyAccountRepository: MoneyAccountRepository

    val currencyChoiceStoreProvider: CurrencyChoiceStoreProvider

    val transactionAmountCalculator: TransactionAmountCalculator
}