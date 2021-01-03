package serg.chuprin.finances.feature.transaction.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.transition.doOnEnd
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.afterTextChanges
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.navigation.TransactionNavigation
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.arguments
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.di.TransactionComponent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionEvent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionFragment : BaseFragment(R.layout.fragment_transaction) {

    @Inject
    lateinit var navigation: TransactionNavigation

    private val viewModel by viewModelFromComponent { component }

    private val component by component { TransactionComponent.get() }

    private val screenArguments by arguments<TransactionScreenArguments>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions {
            if (savedInstanceState == null) {
                doOnEnd {
                    amountEditText?.showKeyboard()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top }
        }

        setupAndPostponeEnterTransition()

        setClickListeners()

        setAmountListener()

        setCategoryPickerResultListener()

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

    private fun setupAndPostponeEnterTransition() {
        postponeEnterTransition()

        with(requireView()) {
            transitionName = screenArguments.transitionName
            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun setAmountListener() {
        amountEditText
            .afterTextChanges()
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
        }
    }

}