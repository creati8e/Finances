package serg.chuprin.finances.feature.onboarding

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.*
import serg.chuprin.finances.core.api.domain.repository.*
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 09.04.2020.
 */
interface OnboardingFeatureDependencies {

    val amountParser: AmountParser

    val resourceManger: ResourceManger

    val amountFormatter: AmountFormatter

    val onboardingNavigation: OnboardingNavigation

    // region Repositories.

    val userRepository: UserRepository

    val currencyRepository: CurrencyRepository

    val onboardingRepository: OnboardingRepository

    val transactionRepository: TransactionRepository

    val moneyAccountRepository: MoneyAccountRepository

    // endregion

    // region Use cases.

    val searchCurrenciesUseCase: SearchCurrenciesUseCase

    // endregion
}

@Component(
    dependencies = [
        CoreUtilsProvider::class,
        CoreManagerProvider::class,
        CoreUseCasesProvider::class,
        CoreNavigationProvider::class,
        CoreFormattersProvider::class,
        CoreRepositoriesProvider::class
    ]
)
internal interface OnboardingFeatureDependenciesComponent : OnboardingFeatureDependencies