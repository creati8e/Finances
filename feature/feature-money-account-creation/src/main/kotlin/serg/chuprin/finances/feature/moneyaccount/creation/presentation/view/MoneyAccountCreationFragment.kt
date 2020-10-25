package serg.chuprin.finances.feature.moneyaccount.creation.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import kotlinx.android.synthetic.main.fragment_money_account_creation.*
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.view.CurrencyChoiceListController
import serg.chuprin.finances.core.api.presentation.extensions.setupToolbar
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.moneyaccount.creation.R
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.di.MoneyAccountCreationComponent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationIntent

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
class MoneyAccountCreationFragment : BaseFragment(R.layout.fragment_money_account_creation) {

    private val viewModel by viewModelFromComponent { MoneyAccountCreationComponent.get() }

    private val currencyChoiceListController
        get() = _currencyChoiceListController!!

    private var _currencyChoiceListController: CurrencyChoiceListController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementTransition()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.dispatchIntent(MoneyAccountCreationIntent.BackPress)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        _currencyChoiceListController = CurrencyChoiceListController(
            context = requireContext(),
            animationContainer = constraintLayout,
            currencyChoiceView = currencyChoiceView,
            chosenCurrencyTextView = chosenCurrencyTextView
        )

        currencyChoiceView.onCurrencyCellChosen = { cell ->
            viewModel.dispatch(CurrencyChoiceIntent.ChooseCurrency(cell))
        }
        currencyChoiceView.onCloseClicked = {
            viewModel.dispatch(CurrencyChoiceIntent.CloseCurrencyPicker)
        }
        currencyChoiceView.onSearchQueryChanged = { searchQuery ->
            viewModel.dispatch(CurrencyChoiceIntent.SearchCurrencies(searchQuery))
        }
        chosenCurrencyTextView.onClick {
            viewModel.dispatch(CurrencyChoiceIntent.ClickOnCurrencyPicker)
        }
        with(viewModel) {
            currencyCellsLiveData(currencyChoiceView::setCells)
            chosenCurrencyDisplayNameLiveData(chosenCurrencyTextView::setText)
            currencyPickerVisibilityLiveData(currencyChoiceListController::showOrHide)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _currencyChoiceListController = null
    }

}