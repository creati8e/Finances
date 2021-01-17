package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.transition.*
import com.google.android.material.transition.MaterialSharedAxis
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_onboarding_accounts_setup.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.feature.onboarding.presentation.OnboardingNavigation
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import serg.chuprin.finances.feature.onboarding.di.OnboardingFeatureComponent
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
class AccountsSetupOnboardingFragment : BaseFragment(R.layout.fragment_onboarding_accounts_setup) {

    private companion object {
        private const val TRANSITION_DURATION = 200L
    }

    @Inject
    lateinit var onboardingNavigation: OnboardingNavigation

    @Inject
    lateinit var amountFormatter: AmountFormatter

    private val viewModel by viewModelFromComponent { component }

    private val component by component {
        OnboardingFeatureComponent.get(findComponentDependencies()).accountsSetupComponent()
    }

    private var textWatcher: TextWatcher? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.All }
        }

        setupBalanceInput()

        setClickListeners()

        with(viewModel) {
            eventsLiveData(::handleEvent)
            stepStateLiveData(::handleStepState)
            balanceLiveData(balanceEditText::setAmount)
            currencySymbolLiveDate(balanceEditText::setCurrencySymbol)
        }
    }

    override fun onStart() {
        super.onStart()
        textWatcher = balanceEditText.doAfterTextChanged { editable ->
            // Check if this event is not self-change.
            if (!balanceEditText.shouldIgnoreChanges) {
                val enteredBalance = editable?.toString().orEmpty()
                viewModel.dispatchIntent(AccountsSetupOnboardingIntent.EnterBalance(enteredBalance))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        balanceEditText.removeTextChangedListener(textWatcher)
        textWatcher = null
    }

    private fun handleEvent(event: AccountsSetupOnboardingEvent) {
        return when (event) {
            AccountsSetupOnboardingEvent.NavigateToDashboard -> {
                onboardingNavigation.navigateToDashboard(navController)
            }
        }
    }

    private fun handleStepState(stepState: AccountsSetupOnboardingStepState) {
        return when (stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                acceptBalanceButton.makeGone()
                balanceEditText.hideKeyboard()
                showQuestionStep(R.string.onboarding_accounts_setup_subtitle_cash_question)
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                acceptBalanceButton.makeGone()
                balanceEditText.hideKeyboard()
                showQuestionStep(R.string.onboarding_accounts_setup_subtitle_bank_card_question)
            }
            is AccountsSetupOnboardingStepState.CashBalanceEnter -> {
                showBalanceEnterStep(stepState.acceptBalanceButtonIsEnabled)
            }
            is AccountsSetupOnboardingStepState.BankCardBalanceEnter -> {
                showBalanceEnterStep(stepState.acceptBalanceButtonIsEnabled)
            }
            is AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                TransitionManager.beginDelayedTransition(constraintLayout)
                buttonsGroup.makeGone()
                titleTextView.makeGone()
                balanceEditText.makeGone()
                acceptBalanceButton.makeGone()
                balanceEditText.hideKeyboard()
                startUsingAppButton.makeVisible()
                subtitleTextView.setText(stepState.message)
            }
        }
    }

    private fun showBalanceEnterStep(acceptBalanceButtonIsEnabled: Boolean) {
        // Animate only if we move from previous state.
        if (buttonsGroup.isVisible) {
            val transitionSet = stepChangeTransition {
                // Animate button itself.
                addTransition(
                    scaleTransition {
                        addTarget(acceptBalanceButton)
                    }
                )
                doOnEnd {
                    balanceEditText.showKeyboard()
                }
            }
            TransitionManager.beginDelayedTransition(constraintLayout, transitionSet)
        }
        buttonsGroup.makeGone()
        balanceEditText.makeVisible()
        acceptBalanceButton.makeVisible()

        acceptBalanceButton.isEnabled = acceptBalanceButtonIsEnabled
        subtitleTextView.setText(R.string.onboarding_accounts_setup_subtitle_enter_current_balance)
    }

    private fun showQuestionStep(@StringRes questionSubtitleStringRes: Int) {
        val transitionSet = stepChangeTransition()
        TransitionManager.beginDelayedTransition(constraintLayout, transitionSet)

        balanceEditText.makeGone()
        buttonsGroup.makeVisible()
        subtitleTextView.setText(questionSubtitleStringRes)
    }

    private fun acceptEnteredBalance() {
        viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton)
    }

    // region Transitions.


    private fun stepChangeTransition(setup: TransitionSet.() -> Unit = {}): TransitionSet {
        return TransitionSet().apply {
            duration = TRANSITION_DURATION
            addTransition(autoTransition(balanceEditText))
            addTransition(autoTransition(subtitleTextView))
            addTransition(scaleTransition {
                addTarget(positiveButton)
                addTarget(negativeButton)
            })
            setup(this)
        }
    }

    private fun autoTransition(view: View): AutoTransition {
        return AutoTransition().apply {
            duration = TRANSITION_DURATION
            addTarget(view)
        }
    }

    private fun scaleTransition(setup: Transition.() -> Unit): Transition {
        return AutoTransition().apply {
            duration = TRANSITION_DURATION
            setup(this)
        }
    }

    // endregion

    private fun setupBalanceInput() {
        with(balanceEditText) {
            isHorizontalFadingEdgeEnabled = true
            setFadingEdgeLength(requireContext().dpToPx(8))
            doOnEditorAction(func = ::acceptEnteredBalance)
            setFormatter { input ->
                amountFormatter.formatInput(input, viewModel.currency)
            }
        }
    }

    private fun setClickListeners() {
        startUsingAppButton.onClick {
            viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnStartUsingAppButton)
        }

        acceptBalanceButton.onClick(::acceptEnteredBalance)

        positiveButton.onClick {
            viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
        }
        negativeButton.onClick {
            viewModel.dispatchIntent(AccountsSetupOnboardingIntent.ClickOnNegativeButton)
        }
    }

}