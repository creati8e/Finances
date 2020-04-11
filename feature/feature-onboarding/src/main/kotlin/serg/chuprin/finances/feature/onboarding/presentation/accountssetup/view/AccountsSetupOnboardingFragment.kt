package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view

import android.graphics.Color
import android.os.Bundle
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.android.material.transition.Scale
import kotlinx.android.synthetic.main.fragment_onboarding_accounts_setup.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.common.di.OnboardingFeatureComponent
import serg.chuprin.finances.feature.onboarding.presentation.common.view.OnboardingContainerFragment

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
class AccountsSetupOnboardingFragment : BaseFragment(R.layout.fragment_onboarding_accounts_setup) {

    private val viewModel by viewModelFromComponent { parentComponent.accountsSetupComponent() }

    private val parentComponent: OnboardingFeatureComponent
        get() = (parentFragment as OnboardingContainerFragment).component

    private var textWatcher: TextWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stepStateLiveData(::handleStepState)

        amountEditText.isHorizontalFadingEdgeEnabled = true
        amountEditText.setFadingEdgeLength(requireContext().dpToPx(8))

        positiveButton.onClick {
            viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
        }
        negativeButton.onClick {
            viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnNegativeButton)
        }
    }

    override fun onStart() {
        super.onStart()
        textWatcher = amountEditText.doOnTextChanged { text, _, _, _ ->
            if (!amountEditText.isVisible) {
                return@doOnTextChanged
            }
            val input = text?.toString().orEmpty()
            val visible = input.toDoubleOrNull() != null
            if (visible != acceptAmountButton.isVisible) {
                TransitionManager.beginDelayedTransition(constraintLayout)
                acceptAmountButton.makeVisibleOrGone(visible)
                if (visible) {
                    amountEditText.setTextColor(Color.BLACK)
                } else {
                    if (input.isNotEmpty()) {
                        amountEditText.setTextColor(Color.RED)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        amountEditText.removeTextChangedListener(textWatcher)
    }

    private fun handleStepState(stepState: AccountsSetupOnboardingStepState) {
        acceptAmountButton.makeGone()
        return when (stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                amountEditText.makeGone()
                buttonsGroup.makeVisible()
                subtitleTextView.setText(R.string.onboarding_accounts_setup_subtitle_cash_question)
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                amountEditText.makeGone()
                buttonsGroup.makeVisible()
                subtitleTextView.setText(R.string.onboarding_accounts_setup_subtitle_bank_card_question)
            }
            is AccountsSetupOnboardingStepState.CashAmountEnter -> {
                val transitionSet = TransitionSet().apply {
                    addTransition(AutoTransition().apply {
                        addTarget(subtitleTextView)
                    })
                    addTransition(Scale().apply {
                        duration = 100L
                        isEntering = false
                        incomingEndScale = 1f
                        incomingStartScale = 0.1f
                        addTarget(positiveButton)
                        addTarget(negativeButton)
                    })
                }
                TransitionManager.beginDelayedTransition(constraintLayout, transitionSet)
                buttonsGroup.makeGone()
                amountEditText.makeVisible()
                subtitleTextView.setText(R.string.onboarding_accounts_setup_subtitle_enter_current_balance)
            }
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                buttonsGroup.makeGone()
                amountEditText.makeVisible()
                subtitleTextView.setText(R.string.onboarding_accounts_setup_subtitle_enter_current_balance)
            }
            AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                // TODO: Handle.
            }
        }
    }

}