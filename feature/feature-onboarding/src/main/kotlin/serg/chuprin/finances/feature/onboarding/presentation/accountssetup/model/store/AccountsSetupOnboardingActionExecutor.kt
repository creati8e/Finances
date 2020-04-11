package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import androidx.core.util.Consumer
import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingActionExecutor @Inject constructor() :
    StoreActionExecutor<AccountsSetupOnboardingIntent, AccountsSetupOnboardingState, AccountsSetupOnboardingEffect, AccountsSetupOnboardingEvent> {

    override fun invoke(
        intent: AccountsSetupOnboardingIntent,
        state: AccountsSetupOnboardingState,
        eventConsumer: Consumer<AccountsSetupOnboardingEvent>,
        actionsFlow: Flow<AccountsSetupOnboardingIntent>
    ): Flow<AccountsSetupOnboardingEffect> {
        TODO("Not yet implemented")
    }

}