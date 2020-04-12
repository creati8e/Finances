package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view

import android.os.Bundle
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import androidx.annotation.StringRes
import androidx.core.transition.doOnEnd
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
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

    private companion object {
        private const val TRANSITION_DURATION = 150L
    }

    private val viewModel by viewModelFromComponent { parentComponent.accountsSetupComponent() }

    private val parentComponent: OnboardingFeatureComponent
        get() = (parentFragment as OnboardingContainerFragment).component

    private var textWatcher: TextWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            stepStateLiveData(::handleStepState)
            formattedAmountLiveData { amount ->
                amountEditText.doIgnoringChanges {
                    setText(amount)
                }
            }
        }

        with(amountEditText) {
            isHorizontalFadingEdgeEnabled = true
            setFadingEdgeLength(requireContext().dpToPx(8))
            doOnEditorAction(func = ::acceptEnteredBalance)
        }

        acceptAmountButton.onClick(::acceptEnteredBalance)

        positiveButton.onClick {
            viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
        }
        negativeButton.onClick {
            viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnNegativeButton)
        }
    }

    override fun onStart() {
        super.onStart()
        amountEditText.doAfterTextChanged { editable ->
            // Check if this event is not self-change.
            if (!amountEditText.shouldIgnoreChanges) {
                val enteredAmount = editable?.toString().orEmpty()
                viewModel.dispatchIntent(AccountsSetupOnboardingIntent.InputAmount(enteredAmount))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        amountEditText.removeTextChangedListener(textWatcher)
        textWatcher = null
    }

    private fun handleStepState(stepState: AccountsSetupOnboardingStepState) {
        return when (stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                acceptAmountButton.makeGone()
                amountEditText.hideKeyboard()
                showQuestionStep(R.string.onboarding_accounts_setup_subtitle_cash_question)
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                acceptAmountButton.makeGone()
                amountEditText.hideKeyboard()
                showQuestionStep(R.string.onboarding_accounts_setup_subtitle_bank_card_question)
            }
            is AccountsSetupOnboardingStepState.CashAmountEnter -> {
                showAmountEnterStep(stepState.acceptButtonIsVisible)
            }
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                showAmountEnterStep(stepState.acceptButtonIsVisible)
            }
            AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                TransitionManager.beginDelayedTransition(constraintLayout)
                buttonsGroup.makeGone()
                titleTextView.makeGone()
                amountEditText.makeGone()
                acceptAmountButton.makeGone()
                amountEditText.hideKeyboard()
                subtitleTextView.setText("Everything is ready!")
            }
        }
    }

    private fun showAmountEnterStep(acceptButtonIsVisible: Boolean) {
        // Animate only if we move from previous state.
        if (buttonsGroup.isVisible) {
            val transitionSet = buildStepChangeTransition(isEntering = false).apply {
                doOnEnd {
                    amountEditText.showKeyboard()
                }
            }
            TransitionManager.beginDelayedTransition(constraintLayout, transitionSet)

        } else if (!acceptAmountButton.isVisible && acceptButtonIsVisible) {
            // Or if button changed its visibility during amount entering.
            acceptAmountButton.scaleY = 0.1f
            acceptAmountButton.scaleX = 0.1f
            val transition = TransitionSet().apply {
                // Animate subtitle.
                addTransition(buildAutoTransitionForSubtitle())
                // Animate button itself.
                addTransition(buildScaleTransition(isEntering = true).apply {
                    addTarget(acceptAmountButton)
                })
            }
            TransitionManager.beginDelayedTransition(constraintLayout, transition)
        }
        buttonsGroup.makeGone()
        amountEditText.makeVisible()
        acceptAmountButton.makeVisibleOrGone(acceptButtonIsVisible)
        subtitleTextView.setText(R.string.onboarding_accounts_setup_subtitle_enter_current_balance)
    }

    private fun showQuestionStep(@StringRes questionSubtitleStringRes: Int) {
        val transitionSet = buildStepChangeTransition(isEntering = true)
        TransitionManager.beginDelayedTransition(constraintLayout, transitionSet)

        amountEditText.makeGone()
        buttonsGroup.makeVisible()
        subtitleTextView.setText(questionSubtitleStringRes)
    }

    private fun acceptEnteredBalance() {
        viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton)
    }

    // region Transitions.

    private fun buildStepChangeTransition(isEntering: Boolean): TransitionSet {
        return TransitionSet().apply {
            addTransition(buildAutoTransitionForSubtitle())
            addTransition(buildScaleTransition(isEntering).apply {
                addTarget(positiveButton)
                addTarget(negativeButton)
            })
        }
    }

    private fun buildAutoTransitionForSubtitle(): AutoTransition {
        return AutoTransition().apply {
            duration = TRANSITION_DURATION
            addTarget(subtitleTextView)
        }
    }

    private fun buildScaleTransition(isEntering: Boolean): Transition {
        return Scale().apply {
            duration = TRANSITION_DURATION
            this.isEntering = isEntering
            if (isEntering) {
                incomingStartScale = 0.1f
                incomingEndScale = 1f
            } else {
                outgoingStartScale = 1f
                outgoingEndScale = 0.1f
            }
        }
    }

    // endregion

}