package serg.chuprin.finances.feature.onboarding.presentation.launch.view

import android.os.Bundle
import android.view.View
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.di.OnboardingFeatureComponent

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingLaunchFragment : BaseFragment(R.layout.fragment_onboarding_launch) {

    private val viewModel by viewModelFromComponent {
        OnboardingFeatureComponent.get(findComponentDependencies())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentStepLiveData(::setCurrentFragment)
    }

    private fun setCurrentFragment(currentStep: OnboardingStep) {
        return when (currentStep) {
            OnboardingStep.COMPLETED -> Unit
            OnboardingStep.ACCOUNTS_SETUP -> {
                OnboardingLaunchFragmentDirections.navigateToAccountsSetupOnboarding().run {
                    navController.navigate(this)
                }
            }
            OnboardingStep.CURRENCY_CHOICE -> {
                OnboardingLaunchFragmentDirections.navigateToCurrencyChoiceOnboarding().run {
                    navController.navigate(this)
                }
            }
        }
    }

}