package serg.chuprin.finances.feature.onboarding.presentation.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_onboarding_container.*
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromProvider
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.di.OnboardingComponent
import serg.chuprin.finances.feature.onboarding.presentation.view.adapter.OnboardingPagerAdapter

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingContainerFragment : BaseFragment(R.layout.fragment_onboarding_container) {

    val component by component { OnboardingComponent.get() }

    private lateinit var pagerAdapter: OnboardingPagerAdapter
    private val viewModel by viewModelFromProvider { component.containerViewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        viewModel.currentStepLiveData(::handleNavigationEvent)
    }

    private fun handleNavigationEvent(onboardingStep: OnboardingStep) {
        val page = when (onboardingStep) {
            OnboardingStep.ACCOUNT_SETUP -> 1
            OnboardingStep.CURRENCY_CHOICE -> 0
            OnboardingStep.COMPLETED -> TODO("Navigate to dasboard")
        }
        onboardingViewPager.setCurrentItem(page, false)
    }

    private fun setupViewPager() {
        pagerAdapter = OnboardingPagerAdapter(this)
        onboardingViewPager.adapter = pagerAdapter
        onboardingViewPager.isUserInputEnabled = false
    }

}