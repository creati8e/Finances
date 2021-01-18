package serg.chuprin.finances.feature.moneyaccounts.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_money_accounts_list.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.fragmentArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.setupToolbar
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.moneyaccounts.R
import serg.chuprin.finances.feature.moneyaccounts.di.MoneyAccountsListComponent
import serg.chuprin.finances.feature.moneyaccounts.presentation.MoneyAccountsListNavigation
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.cells.MoneyAccountCell
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListEvent
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListIntent
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.adapter.renderer.MoneyAccountCellRenderer
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
class MoneyAccountsListFragment : BaseFragment(R.layout.fragment_money_accounts_list) {

    @Inject
    lateinit var navigation: MoneyAccountsListNavigation

    private val viewModel by viewModelFromComponent { component }

    private val component by component {
        MoneyAccountsListComponent.get(fragmentArguments(), findComponentDependencies())
    }

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback()).apply {
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(MoneyAccountCellRenderer())
        clickListener = { cell, _, _ ->
            if (cell is MoneyAccountCell) {
                viewModel.dispatchIntent(MoneyAccountsListIntent.ClickOnMoneyAccount(cell))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top + Edge.Left + Edge.Right }
            moneyAccountsRecyclerView.fit { Edge.Bottom }
            accountCreationFab.fit { Edge.Bottom + Edge.Right }
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        accountCreationFab.onClick {
            viewModel.dispatchIntent(MoneyAccountsListIntent.ClickOnMoneyAccountCreationButton)
        }

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        with(moneyAccountsRecyclerView) {
            adapter = cellsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        with(viewModel) {
            eventsLiveData(::handleEvent)
            cellsLiveData(cellsAdapter::setItems)
            moneyAccountCreationButtonVisibilityLiveData { isVisible ->
                if (isVisible) {
                    accountCreationFab.show()
                } else {
                    accountCreationFab.hide()
                }
            }
        }
    }

    private fun handleEvent(event: MoneyAccountsListEvent) {
        return when (event) {
            is MoneyAccountsListEvent.NavigateToMoneyAccountDetailsScreen -> {
                navigation.navigateToMoneyAccountDetails(
                    navController,
                    event.screenArguments,
                    moneyAccountsRecyclerView.findViewWithTag(event.screenArguments.transitionName)
                )
            }
            is MoneyAccountsListEvent.NavigateToMoneyAccountCreationScreen -> {
                val sharedElementView = accountCreationFab
                navigation.navigateToMoneyAccount(
                    navController,
                    event.screnArguments,
                    sharedElementView
                )
            }
            is MoneyAccountsListEvent.ChooseMoneyAccountAndCloseScreen -> {
                val moneyAccountId = event.moneyAccountId.value
                setFragmentResult(
                    MoneyAccountsListScreenArguments.Picker.REQUEST_KEY,
                    MoneyAccountsListScreenArguments.Picker.Result(moneyAccountId).asBundle()
                )
                navController.navigateUp()
                Unit
            }
        }
    }

}