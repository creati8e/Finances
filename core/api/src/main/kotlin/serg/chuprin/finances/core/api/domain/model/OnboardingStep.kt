package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
enum class OnboardingStep(val order: Int) {
    CURRENCY_CHOICE(1),
    ACCOUNTS_SETUP(2),
    COMPLETED(3);

    companion object {
        val INITIAL = CURRENCY_CHOICE
    }

}