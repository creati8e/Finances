package serg.chuprin.finances.feature.moneyaccount.creation.presentation.view

import android.os.Bundle
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.fragment_money_account_creation.*
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.view.CurrencyChoiceListController
import serg.chuprin.finances.core.api.presentation.extensions.setupToolbar
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.MenuConfig
import serg.chuprin.finances.core.api.presentation.view.extensions.doIgnoringChanges
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.extensions.shortToast
import serg.chuprin.finances.core.api.presentation.view.extensions.shouldIgnoreChanges
import serg.chuprin.finances.core.api.presentation.view.menuConfig
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.moneyaccount.creation.R
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.di.MoneyAccountCreationComponent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationEvent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationIntent

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
class MoneyAccountCreationFragment : BaseFragment(R.layout.fragment_money_account_creation) {

    private val viewModel by viewModelFromComponent { MoneyAccountCreationComponent.get() }

    private val currencyChoiceListController
        get() = _currencyChoiceListController!!

    private var accountNameEditTextTextWatcher: TextWatcher? = null
    private var balanceEditTextTextWatcher: TextWatcher? = null
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
            setHomeAsUpIndicator(R.drawable.ic_close)
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
            eventLiveData(::handleEvent)
            currencyCellsLiveData(currencyChoiceView::setCells)
            chosenCurrencyDisplayNameLiveData(chosenCurrencyTextView::setText)
            currencyPickerVisibilityLiveData(currencyChoiceListController::showOrHide)
            balanceStateLiveData { balanceState ->
                initialBalanceTil.isErrorEnabled = balanceState.hasError
                balanceEditText.doIgnoringChanges {
                    setText(balanceState.formattedAmount)
                }
            }
            savingButtonIsEnabledLiveData { isEnabled ->
                menu?.setSavingMenuItemEnabled(isEnabled)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        balanceEditTextTextWatcher = balanceEditText.doAfterTextChanged { editable ->
            // Check if this event is not self-change.
            if (!balanceEditText.shouldIgnoreChanges) {
                val enteredBalance = editable?.toString().orEmpty()
                viewModel.dispatchIntent(MoneyAccountCreationIntent.EnterBalance(enteredBalance))
            }
        }
        accountNameEditTextTextWatcher = accountNameEditText.doAfterTextChanged { editable ->
            // Check if this event is not self-change.
            if (!accountNameEditText.shouldIgnoreChanges) {
                val enteredName = editable?.toString().orEmpty()
                viewModel.dispatchIntent(MoneyAccountCreationIntent.EnterAccountName(enteredName))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        view?.clearFocus()

        balanceEditText.removeTextChangedListener(balanceEditTextTextWatcher)
        balanceEditTextTextWatcher = null

        accountNameEditText.removeTextChangedListener(accountNameEditTextTextWatcher)
        accountNameEditTextTextWatcher = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _currencyChoiceListController = null
    }

    override fun createMenu(): MenuConfig? {
        return menuConfig {
            addMenu(R.menu.menu_money_account_creation)
            addMenuItemListener { menuItem ->
                if (menuItem.itemId == R.id.menu_action_save) {
                    viewModel.dispatchIntent(MoneyAccountCreationIntent.ClickOnSaveButton)
                }
            }
            addPrepareMenuListener { menu ->
                menu.setSavingMenuItemEnabled(viewModel.savingButtonIsEnabled)
            }
        }
    }

    private fun handleEvent(event: MoneyAccountCreationEvent) {
        return when (event) {
            MoneyAccountCreationEvent.CloseScreen -> {
                navController.navigateUp()
                Unit
            }
            MoneyAccountCreationEvent.ShowAccountCreatedMessage -> {
                shortToast(R.string.money_account_creation_account_created_message)
            }
        }
    }

    private fun Menu.setSavingMenuItemEnabled(isEnabled: Boolean) {
        with(findItem(R.id.menu_action_save)) {
            this.isEnabled = isEnabled
            icon.mutate().alpha = if (isEnabled) 255 else 135
        }
    }

}