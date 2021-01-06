package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingStoreBootstrapper @Inject constructor(
    private val userRepository: UserRepository
) : StoreBootstrapper<AccountsSetupOnboardingAction> {

    override fun invoke(): Flow<AccountsSetupOnboardingAction> {
        return flow {
            val currency = userRepository.getCurrentUser().defaultCurrency
            emit(AccountsSetupOnboardingAction.SetCurrency(currency))
        }
    }

}