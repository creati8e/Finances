package serg.chuprin.finances.feature.moneyaccount.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.repository.*
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreFactoryApi
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
interface MoneyAccountDependencies : FeatureDependencies {
    val amountParser: AmountParser
    val amountFormatter: AmountFormatter

    val resourceManger: ResourceManger

    val userRepository: UserRepository
    val categoryRepository: CategoryRepository
    val currencyRepository: CurrencyRepository
    val transactionRepository: TransactionRepository
    val moneyAccountRepository: MoneyAccountRepository

    val currencyChoiceStoreProvider: CurrencyChoiceStoreFactoryApi

    val transactionAmountCalculator: TransactionAmountCalculator
    val moneyAccountBalanceCalculator: MoneyAccountBalanceCalculator
}