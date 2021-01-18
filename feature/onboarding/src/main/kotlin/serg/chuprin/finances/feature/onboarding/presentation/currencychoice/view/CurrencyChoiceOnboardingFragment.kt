package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.view

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.transition.MaterialSharedAxis
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_onboarding_currency_choice.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.view.CurrencyChoiceListController
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisible
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.CurrencyChoiceOnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.CurrencyChoiceOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.view.CurrencyChoiceOnboardingFragmentDirections.navigateFromCurrencyChoiceOnboardingToAccountsSetupOnboarding
import serg.chuprin.finances.feature.onboarding.di.OnboardingFeatureComponent

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
class CurrencyChoiceOnboardingFragment :
    BaseFragment(R.layout.fragment_onboarding_currency_choice) {

    private val viewModel by viewModelFromComponent {
        OnboardingFeatureComponent.get(findComponentDependencies()).currencyChoiceComponent()
    }

    private val currencyChoiceListController
        get() = _currencyChoiceListController!!

    private var _currencyChoiceListController: CurrencyChoiceListController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.dispatchIntent(CurrencyChoiceOnboardingIntent.BackPress)
        }

        val backward = MaterialSharedAxis(MaterialSharedAxis.X, false)
        reenterTransition = backward

        val forward = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = forward
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.All }
        }

        _currencyChoiceListController = CurrencyChoiceListController(
            animationContainer = constraintLayout,
            chosenCurrencyTextView = chosenCurrencyTextView,
            currencyChoiceView = currencyChoiceView
        )
        animateFab(savedInstanceState, view)

        currencyChoiceView.onCurrencyCellChosen = { cell ->
            viewModel.dispatch(CurrencyChoiceIntent.ClickOnCurrencyCell(cell))
        }
        currencyChoiceView.onCloseClicked = {
            viewModel.dispatch(CurrencyChoiceIntent.ClickOnCloseCurrencyPicker)
        }
        currencyChoiceView.onSearchQueryChanged = { searchQuery ->
            viewModel.dispatch(CurrencyChoiceIntent.EnterCurrencySearchQuery(searchQuery))
        }
        chosenCurrencyTextView.onClick {
            viewModel.dispatch(CurrencyChoiceIntent.ClickOnCurrencyPicker)
        }
        doneButton.onClick {
            viewModel.dispatchIntent(CurrencyChoiceOnboardingIntent.ClickOnDoneButton)
        }

        with(viewModel) {
            eventsLiveData(::handleEvent)
            cellsLiveData(currencyChoiceView::setCells)
            doneButtonEnabledLiveData(doneButton::setEnabled)
            chosenCurrencyDisplayNameLiveData(chosenCurrencyTextView::setText)
            currencyPickerVisibilityLiveData(currencyChoiceListController::showOrHide)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _currencyChoiceListController = null
    }

    private fun handleEvent(event: CurrencyChoiceOnboardingEvent) {
        return when (event) {
            CurrencyChoiceOnboardingEvent.CloseApp -> {
                requireActivity().finishAndRemoveTask()
            }
            CurrencyChoiceOnboardingEvent.NavigateToAccountsSetup -> {
                navigateFromCurrencyChoiceOnboardingToAccountsSetupOnboarding().run {
                    navController.navigate(this)
                }
            }
        }
    }

    private fun animateFab(savedInstanceState: Bundle?, view: View) {

        fun showFabTextAndExtend() {
            doneButton.setText(R.string.onboarding_currency_choice_button)
            doneButton.extend()
        }

        // Animate on first screen show.
        if (savedInstanceState == null) {
            view.postDelayed(
                {
                    doneButton?.show(object : ExtendedFloatingActionButton.OnChangedCallback() {
                        override fun onShown(extendedFab: ExtendedFloatingActionButton?) {
                            showFabTextAndExtend()
                        }
                    })
                },
                400
            )
        } else {
            doneButton.makeVisible()
            showFabTextAndExtend()
        }
    }

}