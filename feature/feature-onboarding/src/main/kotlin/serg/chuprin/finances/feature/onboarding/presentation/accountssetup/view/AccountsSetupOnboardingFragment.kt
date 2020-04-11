package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view

import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.common.di.OnboardingFeatureComponent
import serg.chuprin.finances.feature.onboarding.presentation.common.view.OnboardingContainerFragment

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
class AccountsSetupOnboardingFragment : BaseFragment(R.layout.fragment_onboarding_accounts_setup) {

    private val viewModel by viewModelFromComponent { parentComponent.accountsSetupComponent() }

    private val parentComponent: OnboardingFeatureComponent
        get() = (parentFragment as OnboardingContainerFragment).component

}