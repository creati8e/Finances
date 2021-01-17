package serg.chuprin.finances.feature.moneyaccount.details.presentation.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_money_account_details.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.decoration.CellDividerDecoration
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.arguments
import serg.chuprin.finances.core.api.presentation.view.extensions.getAttributeColor
import serg.chuprin.finances.core.api.presentation.view.extensions.getColorInt
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.moneyaccount.details.R
import serg.chuprin.finances.feature.moneyaccount.details.di.MoneyAccountDetailsComponent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.MoneyAccountDetailsNavigation
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsEvent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsIntent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.adapter.MoneyAccountDetailsTransactionsAdapter
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsFragment : BaseFragment(R.layout.fragment_money_account_details) {

    @Inject
    lateinit var navigation: MoneyAccountDetailsNavigation

    private val viewModel by viewModelFromComponent { component }

    private val cellsAdapter = MoneyAccountDetailsTransactionsAdapter(
        onTransactionClicked = { transactionCell ->
            viewModel.dispatchIntent(
                MoneyAccountDetailsIntent.ClickOnTransactionCell(transactionCell)
            )
        }
    )

    private val screenArguments by arguments<MoneyAccountDetailsScreenArguments>()

    private val component by component {
        MoneyAccountDetailsComponent.get(screenArguments, findComponentDependencies())
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
            transactionsRecyclerView.fit { Edge.Bottom }
        }

        view.transitionName = screenArguments.transitionName
        postponeEnterTransition()
        transactionsRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

        setupClickListeners()

        setupRecyclerView()

        with(viewModel) {
            eventsLiveData(::handleEvent)
            isFavoriteLiveData(::showIsFavorite)
            cellsLiveData(cellsAdapter::setItems)
            balanceLiveData(balanceTextView::setText)
            accountNameLiveData(accountNameTextView::setText)
        }
    }

    private fun setupClickListeners() {
        backButton.onClick {
            navController.navigateUp()
        }
        favoriteImageView.onClick {
            viewModel.dispatchIntent(MoneyAccountDetailsIntent.ClickOnFavoriteIcon)
        }
        settingImageView.onClick {
            viewModel.dispatchIntent(MoneyAccountDetailsIntent.ClickOnEditingButton)
        }
    }

    private fun setupRecyclerView() {
        with(transactionsRecyclerView) {
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                CellDividerDecoration(
                    requireContext(),
                    cellsAdapter,
                    marginEndDp = -8,
                    marginStartDp = 36
                )
            )
        }
    }

    private fun handleEvent(event: MoneyAccountDetailsEvent) {
        return when (event) {
            MoneyAccountDetailsEvent.CloseScreen -> {
                navController.navigateUp()
                Unit
            }
            is MoneyAccountDetailsEvent.NavigateToTransactionScreen -> {
                val sharedElementView = transactionsRecyclerView.findViewWithTag<View>(
                    event.screenArguments.transitionName
                )
                navigation.navigateToTransaction(
                    navController,
                    event.screenArguments,
                    sharedElementView
                )
            }
            is MoneyAccountDetailsEvent.NavigateToMoneyAccountEditingScreen -> {
                navigation.navigateToMoneyAccount(
                    navController,
                    event.screenArguments,
                    settingImageView
                )
            }
        }
    }

    private fun showIsFavorite(isFavorite: Boolean) {
        val tintColor = if (isFavorite) {
            requireContext().getColorInt(R.color.colorFavoriteOrange)
        } else {
            requireContext().getAttributeColor(android.R.attr.textColorPrimary)
        }
        favoriteImageView.imageTintList = ColorStateList.valueOf(tintColor)
    }

}