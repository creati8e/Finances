package serg.chuprin.finances.feature.transaction.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.afterTextChanges
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.di.TransactionComponent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionEvent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionFragment : BaseFragment(R.layout.fragment_transaction) {

    private val viewModel by viewModelFromComponent { TransactionComponent.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top }
        }

        saveIcon.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnSaveButton)
        }

        closeIcon.onClick {
            viewModel.dispatchIntent(TransactionIntent.ClickOnCloseButton)
        }

        amountEditText
            .afterTextChanges()
            .filterNot { event -> event.view.shouldIgnoreChanges }
            .onEach { event ->
                val str = event.editable?.toString().orEmpty()
                viewModel.dispatchIntent(TransactionIntent.EnterAmount(str))
            }
            .launchIn(lifecycleScope)

        if (savedInstanceState == null) {
            amountEditText.showKeyboard()
        }

        with(viewModel) {
            eventLiveData(::handleEvent)
            chosenDateLiveData(chosenDateTextView::setText)
            isSaveButtonEnabledLiveData(saveIcon::setEnabled)
            chosenCategoryLiveData(chosenCategoryTextView::setText)
            chosenMoneyAccountLiveData(chosenMoneyAccountTextView::setText)
            enteredAmountLiveData { enteredAmount ->
                amountEditText.doIgnoringChanges {
                    setText(enteredAmount.formatted)
                }
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
        }
    }

}