package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
sealed class AccountsSetupOnboardingIntent {

    /**
     * User answered positively on current question.
     */
    object ClickOnPositiveButton : AccountsSetupOnboardingIntent()

    /**
     * User answered negatively on current question.
     */
    object ClickOnNegativeButton : AccountsSetupOnboardingIntent()

    /**
     * User entered some account balance and want to accept it.
     */
    object ClickOnAcceptBalanceButton : AccountsSetupOnboardingIntent()

    object ClickOnStartUsingAppButton : AccountsSetupOnboardingIntent()

    class EnterBalance(
        val enteredBalance: String
    ) : AccountsSetupOnboardingIntent()

}