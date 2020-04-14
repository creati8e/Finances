package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.di

import dagger.Subcomponent
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.viewmodel.AccountsSetupOnboardingViewModel
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view.AccountsSetupOnboardingFragment

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
@ScreenScope
@Subcomponent(modules = [AccountsSetupOnboardingModule::class])
interface AccountsSetupOnboardingComponent : ViewModelComponent<AccountsSetupOnboardingViewModel> {

    fun inject(fragment: AccountsSetupOnboardingFragment)

}