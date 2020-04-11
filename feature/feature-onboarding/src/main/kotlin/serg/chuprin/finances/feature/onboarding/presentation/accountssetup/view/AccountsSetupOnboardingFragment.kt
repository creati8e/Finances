package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import androidx.annotation.StringRes
import androidx.core.transition.doOnEnd
import androidx.core.view.isVisible
import com.google.android.material.transition.Scale
import kotlinx.android.synthetic.main.fragment_onboarding_accounts_setup.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.AmountFormatter
import serg.chuprin.finances.core.api.presentation.view.AmountTextWatcher
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.common.di.OnboardingFeatureComponent
import serg.chuprin.finances.feature.onboarding.presentation.common.view.OnboardingContainerFragment
import java.util.*

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
class AccountsSetupOnboardingFragment : BaseFragment(R.layout.fragment_onboarding_accounts_setup) {

    private val viewModel by viewModelFromComponent { parentComponent.accountsSetupComponent() }

    private val parentComponent: OnboardingFeatureComponent
        get() = (parentFragment as OnboardingContainerFragment).component

    private var amountFormatterTextWatcher: AmountTextWatcher? = null

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

    override fun onStop() {
        super.onStop()
        amountEditText.removeTextChangedListener(amountFormatterTextWatcher)
        amountFormatterTextWatcher = null
    }

    private fun handleStepState(stepState: AccountsSetupOnboardingStepState) {
        acceptAmountButton.makeGone()
        return when (stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                showQuestionStep(R.string.onboarding_accounts_setup_subtitle_cash_question)
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                showQuestionStep(R.string.onboarding_accounts_setup_subtitle_bank_card_question)
            }
            is AccountsSetupOnboardingStepState.CashAmountEnter -> {
                animateAmountEnterStep()
            }
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                animateAmountEnterStep()
            }
            AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                // TODO: Handle.
            }
        }
    }

    private fun showQuestionStep(@StringRes questionSubtitleStringRes: Int) {
        amountEditText.makeGone()
        buttonsGroup.makeVisible()
        subtitleTextView.setText(questionSubtitleStringRes)
    }

    private fun animateAmountEnterStep() {
        setupAmountTextWatcher()
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
            doOnEnd {
                amountEditText.showKeyboard()
            }
        }
        TransitionManager.beginDelayedTransition(constraintLayout, transitionSet)

        buttonsGroup.makeGone()
        amountEditText.makeVisible()
        subtitleTextView.setText(R.string.onboarding_accounts_setup_subtitle_enter_current_balance)
    }

    private fun setupAmountTextWatcher() {
        if (amountFormatterTextWatcher == null) {
            amountFormatterTextWatcher = AmountTextWatcher(
                editText = amountEditText,
                amountFormatter = AmountFormatter(),
                // TODO: Use currency from state.
                currency = Currency.getInstance(Locale.getDefault()),
                onAmountEntered = { visible ->
                    if (visible != acceptAmountButton.isVisible) {
                        TransitionManager.beginDelayedTransition(constraintLayout)
                        acceptAmountButton.makeVisibleOrGone(visible)
                    }
                }
            )
            amountEditText.addTextChangedListener(amountFormatterTextWatcher)
        }
    }

}