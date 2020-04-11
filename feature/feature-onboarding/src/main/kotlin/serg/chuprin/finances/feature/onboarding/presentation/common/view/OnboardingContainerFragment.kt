package serg.chuprin.finances.feature.onboarding.presentation.common.view

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.fragment_onboarding_container.*
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromProvider
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.common.di.OnboardingFeatureComponent
import serg.chuprin.finances.feature.onboarding.presentation.common.view.adapter.OnboardingPagerAdapter
import javax.inject.Inject


/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingContainerFragment : BaseFragment(R.layout.fragment_onboarding_container) {

    @Inject
    lateinit var navigation: OnboardingNavigation

    val component by component { OnboardingFeatureComponent.get() }

    private lateinit var pagerAdapter: OnboardingPagerAdapter
    private val viewModel by viewModelFromProvider { component.viewModel }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        viewModel.currentStepLiveData(::handleNavigationEvent)
    }

    private fun handleNavigationEvent(onboardingStep: OnboardingStep) {
        val page = when (onboardingStep) {
            OnboardingStep.ACCOUNT_SETUP -> {
                // TODO: Handle fake drag properly when accounts setup is first fragment.
                1
//                if (onboardingViewPager.beginFakeDrag()) {
//                    animatePageChange()
//                }
//                return
            }
            OnboardingStep.CURRENCY_CHOICE -> 0
            OnboardingStep.COMPLETED -> {
                navigation.navigateToDashboard(navController)
                return
            }
        }
        onboardingViewPager.setCurrentItem(page, false)
    }

    private fun setupViewPager() {
        pagerAdapter = OnboardingPagerAdapter(this)
        onboardingViewPager.adapter = pagerAdapter
        onboardingViewPager.isUserInputEnabled = false
    }

    private var oldDragPosition = 0

    private fun animatePageChange() {
        ValueAnimator
            .ofInt(0, onboardingViewPager.width - 1)
            .run {
                doOnCancel {
                    onboardingViewPager.endFakeDrag()
                }
                doOnEnd {
                    onboardingViewPager.endFakeDrag()
                }
                addUpdateListener { animation ->
                    val dragPosition = animation.animatedValue as Int
                    val dragOffset: Int = dragPosition - oldDragPosition
                    oldDragPosition = dragPosition
                    onboardingViewPager.fakeDragBy(dragOffset * -1f)
                }
                duration = 400L
                interpolator = FastOutSlowInInterpolator()
                start()
            }
    }

}