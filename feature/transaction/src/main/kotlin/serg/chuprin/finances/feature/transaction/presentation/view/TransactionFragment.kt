package serg.chuprin.finances.feature.transaction.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.transition.doOnEnd
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.shape.ShapeAppearanceModel
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.afterTextChanges
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.extensions.toLocalDateUTC
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.feature.transaction.presentation.TransactionNavigation
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.dialog.info.InfoDialogArguments
import serg.chuprin.finances.core.api.presentation.view.dialog.info.InfoDialogFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.arguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.showDialog
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.di.TransactionComponent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionEvent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent
import serg.chuprin.finances.feature.transaction.presentation.view.controller.TransactionOperationTabsController
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionFragment :
    BaseFragment(R.layout.fragment_transaction),
    InfoDialogFragment.Callback {

    private companion object {
        private const val RC_UNSAVED_CHANGED_DIALOG = 12312
        private const val RC_TRANSACTION_DELETION_DIALOG = 12313
    }

    @Inject
    lateinit var navigation: TransactionNavigation

    @Inject
    lateinit var amountFormatter: AmountFormatter

    private val viewModel by viewModelFromComponent { component }

    private val screenArguments by arguments<TransactionScreenArguments>()

    private val component by component {
        TransactionComponent.get(screenArguments, findComponentDependencies())
    }

    private val tabsController = TransactionOperationTabsController()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions(
            enterTransitionSetup = {
                val radius = requireContext().dpToPx(28).toFloat()
                startShapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
                endShapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)

                if (savedInstanceState == null
                    && screenArguments is TransactionScreenArguments.Creation
                ) {
                    doOnEnd {
                        amountEditText?.showKeyboard()
                    }
                }
            },
            returnTransitionSetup = {
                val radius = requireContext().dpToPx(28).toFloat()
                startShapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
                endShapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top + Edge.Left + Edge.Right }
        }

        setupAndPostponeEnterTransition()

        setClickListeners()

        setAmountInput()

        setCategoryPickerResultListener()
        setMoneyAccountPickerResultListener()

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.dispatchIntent(TransactionIntent.ClickOnCloseButton)
        }

        with(viewModel) {
            eventLiveData(::handleEvent)
            chosenDateLiveData(chosenDateTextView::setText)
            enteredAmountLiveData(amountEditText::setAmount)
            isSaveButtonEnabledLiveData(saveIcon::setEnabled)
            chosenCategoryLiveData(chosenCategoryTextView::setText)
            currencySymbolLiveData(amountEditText::setCurrencySymbol)
            chosenMoneyAccountLiveData(chosenMoneyAccountTextView::setText)
            transactionDeletionButtonVisibilityLiveDate(deleteTransactionButton::makeVisibleOrGone)

            chosenOperationLiveData { operation ->
                tabsController.selectTabForOperation(transactionTypeTabLayout, operation)
            }
        }
    }

    override fun onInfoDialogPositiveButtonClick(requestCode: Int) {
        if (requestCode == RC_UNSAVED_CHANGED_DIALOG) {
            viewModel.dispatchIntent(TransactionIntent.ClickOnSaveButton)
        } else if (requestCode == RC_TRANSACTION_DELETION_DIALOG) {
            viewModel.dispatchIntent(TransactionIntent.ClickOnConfirmTransactionDeletion)
        }
    }

    override fun onInfoDialogNegativeButtonClick(requestCode: Int) {
        if (requestCode == RC_UNSAVED_CHANGED_DIALOG) {
            viewModel.dispatchIntent(TransactionIntent.ClickOnUnsavedChangedDialogNegativeButton)
        }
    }

    private fun setupAndPostponeEnterTransition() {
        postponeEnterTransition()

        with(requireView()) {
            transitionName = screenArguments.transitionName
            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun setAmountInput() {
        amountEditText.isHorizontalFadingEdgeEnabled = true
        amountEditText.setFadingEdgeLength(requireContext().dpToPx(8))
        amountEditText.setFormatter { input ->
            val currency = viewModel.currency
            if (currency == null) {
                "0"
            } else {
                amountFormatter.formatInput(input, currency)
            }
        }

        amountEditText
            .afterTextChanges()
            .skipInitialValue()
            .filterNot { event -> event.view.shouldIgnoreChanges }
            .onEach { event ->
                val str = event.editable?.toString().orEmpty()
                viewModel.dispatchIntent(TransactionIntent.EnterAmount(str))
            }
            .launchIn(lifecycleScope)
    }

    private fun setClickListeners() {
        saveIcon.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnSaveButton)
        }
        closeIcon.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnCloseButton)
        }
        categoryLayout.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnCategory)
        }
        dateLayout.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnDate)
        }
        moneyAccountLayout.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnMoneyAccount)
        }
        deleteTransactionButton.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnDeleteTransaction)
        }

        tabsController.listenTabChanges(transactionTypeTabLayout, lifecycleScope) { operation ->
            viewModel.dispatchIntent(TransactionIntent.ClickOnOperationType(operation))
        }
    }

    private fun setMoneyAccountPickerResultListener() {
        val pickerRequestKey = MoneyAccountsListScreenArguments.Picker.REQUEST_KEY
        setFragmentResultListener(pickerRequestKey) { requestKey, bundle ->
            if (requestKey == MoneyAccountsListScreenArguments.Picker.REQUEST_KEY) {
                val result = MoneyAccountsListScreenArguments.Picker.Result.fromBundle(bundle)
                viewModel.dispatchIntent(TransactionIntent.ChooseMoneyAccount(result.moneyAccountId))
            }
        }
    }

    private fun setCategoryPickerResultListener() {
        val pickerRequestKey = CategoriesListScreenArguments.Picker.REQUEST_KEY
        setFragmentResultListener(pickerRequestKey) { requestKey, bundle ->
            if (requestKey == requestKey) {
                val result = CategoriesListScreenArguments.Picker.Result.fromBundle(bundle)
                viewModel.dispatchIntent(TransactionIntent.ChooseCategory(result.categoryId))
            }
        }
    }

    private fun handleEvent(event: TransactionEvent) {
        return when (event) {
            TransactionEvent.CloseScreen -> {
                amountEditText.hideKeyboard()
                navController.navigateUp()
                Unit
            }
            is TransactionEvent.NavigateToCategoryPickerScreen -> {
                amountEditText.hideKeyboard()
                navigation.navigateToCategoryPicker(navController, event.screenArguments)
            }
            is TransactionEvent.ShowDatePicker -> {
                showDatePicker(event)
            }
            is TransactionEvent.NavigateToMoneyAccountPickerScreen -> {
                amountEditText.hideKeyboard()
                navigation.navigateToMoneyAccountPicker(navController, event.screenArguments)
            }
            is TransactionEvent.ShowUnsavedChangedDialog -> {
                val arguments = InfoDialogArguments(
                    title = null,
                    negativeText = getString(CoreR.string.no),
                    positiveText = getString(CoreR.string.yes),
                    message = event.message,
                    callbackRequestCode = RC_UNSAVED_CHANGED_DIALOG
                )
                showDialog<InfoDialogFragment>(childFragmentManager, arguments)
            }
            TransactionEvent.ShowTransactionDeletionDialog -> {
                val arguments = InfoDialogArguments(
                    title = null,
                    negativeText = getString(CoreR.string.no),
                    positiveText = getString(CoreR.string.yes),
                    message = getString(R.string.are_you_sure),
                    callbackRequestCode = RC_TRANSACTION_DELETION_DIALOG
                )
                showDialog<InfoDialogFragment>(childFragmentManager, arguments)
            }
        }
    }

    private fun showDatePicker(event: TransactionEvent.ShowDatePicker) {
        MaterialDatePicker.Builder
            .datePicker()
            .setSelection(
                event.localDate
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli()
            )
            .setTheme(R.style.MaterialDatePicker)
            .build()
            .apply {
                addOnPositiveButtonClickListener { dateMillis ->
                    val localDate = Date(dateMillis).toLocalDateUTC()
                    viewModel.dispatchIntent(TransactionIntent.ChooseDate(localDate))
                }
            }
            .show(childFragmentManager, "MaterialDatePicker_TAG")
    }

}