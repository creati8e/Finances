package serg.chuprin.finances.feature.onboarding.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.repository.*
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreFactoryApi
import serg.chuprin.finances.feature.onboarding.presentation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 09.04.2020.
 */
interface OnboardingFeatureDependencies : FeatureDependencies {

    val amountParser: AmountParser

    val resourceManger: ResourceManger

    val amountFormatter: AmountFormatter

    val onboardingNavigation: OnboardingNavigation

    val currencyChoiceStoreProvider: CurrencyChoiceStoreFactoryApi

    // region Repositories.

    val userRepository: UserRepository

    val currencyRepository: CurrencyRepository

    val onboardingRepository: OnboardingRepository

    val transactionRepository: TransactionRepository

    val moneyAccountRepository: MoneyAccountRepository

    val categoryRepository: CategoryRepository

    // endregion

}