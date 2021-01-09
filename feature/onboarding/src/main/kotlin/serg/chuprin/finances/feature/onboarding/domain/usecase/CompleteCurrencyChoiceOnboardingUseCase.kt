package serg.chuprin.finances.feature.onboarding.domain.usecase

import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 08.04.2020.
 *
 * Completes currency choice onboarding by creating [User] with
 * default currency set and default data period type.
 */
class CompleteCurrencyChoiceOnboardingUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val onboardingRepository: OnboardingRepository
) {

    suspend fun execute(chosenCurrency: Currency) {
        createOnboardedUser(chosenCurrency)
        onboardingRepository.onboardingStep = OnboardingStep.ACCOUNTS_SETUP
    }

    private suspend fun createOnboardedUser(chosenCurrency: Currency) {
        val incompleteUser = userRepository.getIncompleteUser()
        val onboardedUser = User(
            id = incompleteUser.id,
            email = incompleteUser.email,
            photoUrl = incompleteUser.photoUrl,
            dataPeriodType = DataPeriodType.DEFAULT,
            displayName = incompleteUser.displayName,
            defaultCurrencyCode = chosenCurrency.currencyCode
        )
        userRepository.updateUser(onboardedUser)
    }

}