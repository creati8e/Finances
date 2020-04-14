package serg.chuprin.finances.feature.onboarding.presentation.common.view

import android.os.Bundle
import android.view.View
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.common.di.OnboardingFeatureComponent

/**
 * Created by Sergey Chuprin on 06.04.2020.
 *
 * TODO: Rename to OnboardingLaunch
 */
class OnboardingContainerFragment : BaseFragment(R.layout.fragment_onboarding_container) {

    private val viewModel by viewModelFromComponent { OnboardingFeatureComponent.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentStepLiveData(::setCurrentFragment)
    }

    private fun setCurrentFragment(currentStep: OnboardingStep) {
        return when (currentStep) {
            OnboardingStep.COMPLETED -> Unit
            OnboardingStep.ACCOUNT_SETUP -> {
                navController.navigate(R.id.action_onboarding_to_accounts_setup)
            }
            OnboardingStep.CURRENCY_CHOICE -> {
                navController.navigate(R.id.action_onboarding_to_currency_choice)
            }
        }
    }

}