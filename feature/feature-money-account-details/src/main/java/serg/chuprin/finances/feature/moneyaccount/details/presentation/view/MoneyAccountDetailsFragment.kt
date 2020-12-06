package serg.chuprin.finances.feature.moneyaccount.details.presentation.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_money_account_details.*
import serg.chuprin.finances.core.api.presentation.extensions.arguments
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.decoration.CellDividerDecoration
import serg.chuprin.finances.core.api.presentation.view.extensions.getAttributeColor
import serg.chuprin.finances.core.api.presentation.view.extensions.getColorInt
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.moneyaccount.details.R
import serg.chuprin.finances.feature.moneyaccount.details.presentation.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.feature.moneyaccount.details.presentation.di.MoneyAccountDetailsComponent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsEvent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsIntent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.adapter.MoneyAccountDetailsTransactionsAdapter

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsFragment : BaseFragment(R.layout.fragment_money_account_details) {

    private val screenArguments by arguments<MoneyAccountDetailsScreenArguments>()

    private val viewModel by viewModelFromComponent {
        MoneyAccountDetailsComponent.get(screenArguments)
    }

    private val cellsAdapter = MoneyAccountDetailsTransactionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)!!.apply {
            transitionName = screenArguments.transitionName
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.onClick {
            navController.navigateUp()
        }

        favoriteImageView.onClick {
            viewModel.dispatchIntent(MoneyAccountDetailsIntent.ClickOnFavoriteIcon)
        }

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

        with(viewModel) {
            eventsLiveData(::handleEvent)
            isFavoriteLiveData(::showIsFavorite)
            cellsLiveData(cellsAdapter::setItems)
            balanceLiveData(balanceTextView::setText)
            accountNameLiveData(accountNameTextView::setText)
        }
    }

    private fun handleEvent(event: MoneyAccountDetailsEvent) {
        return when (event) {
            MoneyAccountDetailsEvent.CloseScreen -> {
                navController.navigateUp()
                Unit
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