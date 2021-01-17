package serg.chuprin.finances.feature.moneyaccount.creation.presentation.view

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_money_account_creation.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.view.CurrencyChoiceListController
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.MenuConfig
import serg.chuprin.finances.core.api.presentation.view.extensions.doIgnoringChanges
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.arguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.setToolbarTitle
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.setupToolbar
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.shortToast
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.extensions.shouldIgnoreChanges
import serg.chuprin.finances.core.api.presentation.view.menuConfig
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.moneyaccount.creation.R
import serg.chuprin.finances.feature.moneyaccount.creation.di.MoneyAccountCreationComponent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationEvent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationIntent
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
class MoneyAccountCreationFragment : BaseFragment(R.layout.fragment_money_account_creation) {

    @Inject
    lateinit var amountFormatter: AmountFormatter

    private val viewModel by viewModelFromComponent { component }

    private val component by component {
        MoneyAccountCreationComponent.get(screenArguments, findComponentDependencies())
    }

    private val currencyChoiceListController
        get() = _currencyChoiceListController!!

    private val screenArguments by arguments<MoneyAccountScreenArguments>()

    private var accountNameEditTextTextWatcher: TextWatcher? = null
    private var balanceEditTextTextWatcher: TextWatcher? = null
    private var _currencyChoiceListController: CurrencyChoiceListController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.dispatchIntent(MoneyAccountCreationIntent.BackPress)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top + Edge.Left + Edge.Right }
            currencyChoiceView.fit { Edge.Bottom }
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
        }

        view.transitionName = screenArguments.transitionName

        balanceEditText.setFormatter { input ->
            val currency = viewModel.currency
            if (currency == null) {
                "0"
            } else {
                amountFormatter.formatInput(input, currency)
            }
        }

        _currencyChoiceListController = CurrencyChoiceListController(
            animationContainer = constraintLayout,
            chosenCurrencyTextView = chosenCurrencyTextView,
            currencyChoiceView = currencyChoiceView
        )

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
        with(viewModel) {
            eventLiveData(::handleEvent)
            toolbarTitleLiveData(::setToolbarTitle)
            currencyCellsLiveData(currencyChoiceView::setCells)
            chosenCurrencyDisplayNameLiveData(chosenCurrencyTextView::setText)
            currencyPickerIsClickableLiveData(chosenCurrencyTextView::setEnabled)
            currencyPickerVisibilityLiveData(currencyChoiceListController::showOrHide)
            savingButtonIsEnabledLiveData { isEnabled ->
                menu?.setSavingMenuItemEnabled(isEnabled)
            }
            balanceStateLiveData(balanceEditText::setAmount)
            accountNameLiveData { accountName ->
                accountNameEditText.doIgnoringChanges {
                    setText(accountName)
                    setSelection(accountName.length, accountName.length)
                }
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

    override fun createMenu(): MenuConfig {
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