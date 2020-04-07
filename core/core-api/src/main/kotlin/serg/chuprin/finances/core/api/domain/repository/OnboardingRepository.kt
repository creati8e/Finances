package serg.chuprin.finances.core.api.domain.repository

import serg.chuprin.finances.core.api.domain.model.OnboardingStep

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
interface OnboardingRepository {

    val onboardingStep: OnboardingStep

}