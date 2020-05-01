package serg.chuprin.finances.feature.onboarding.domain

import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
data class OnboardingMoneyAccountCreationParams(
    val accountName: String,
    val balance: BigDecimal
)