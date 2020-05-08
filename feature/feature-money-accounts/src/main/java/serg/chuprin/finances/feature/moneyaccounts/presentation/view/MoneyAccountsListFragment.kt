package serg.chuprin.finances.feature.moneyaccounts.presentation.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.transition.addListener
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_money_accounts_list.*
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.navigation.MoneyAccountsListNavigation
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.SHARED_ELEMENT_TRANSITION_DURATION
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.feature.moneyaccounts.R
import serg.chuprin.finances.feature.moneyaccounts.di.MoneyAccountsListComponent
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

    private val component by component { MoneyAccountsListComponent.get() }

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback<BaseCell>()).apply {
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
        sharedElementEnterTransition = MaterialContainerTransform(requireContext()).apply {
            containerColor = Color.WHITE
            duration = SHARED_ELEMENT_TRANSITION_DURATION
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            startShapeAppearanceModel = ShapeAppearanceModel().withCornerSize(
                requireContext().dpToPx(16).toFloat()
            )
            addListener(onEnd = { accountCreationFab?.show() })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        }
    }

    private fun handleEvent(event: MoneyAccountsListEvent) {
        return when (event) {
            is MoneyAccountsListEvent.NavigateToMoneyAccountDetailsScreen -> {
                val transitionName =
                    "${getString(R.string.transition_money_account)}${event.moneyAccountId.value}"
                val sharedElementView =
                    moneyAccountsRecyclerView.findViewWithTag<View>(transitionName)
                navigation.navigateToMoneyAccountDetails(
                    navController,
                    event.moneyAccountId,
                    sharedElementView
                )
            }
        }
    }

}