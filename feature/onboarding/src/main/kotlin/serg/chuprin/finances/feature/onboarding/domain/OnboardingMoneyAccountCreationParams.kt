package serg.chuprin.finances.feature.onboarding.domain

import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 13.04.2020.
 *
 * Class that holds money account parameters retrieved from money accounts creation onboarding step.
 */
data class OnboardingMoneyAccountCreationParams(
    val accountName: String,
    val balance: BigDecimal
)