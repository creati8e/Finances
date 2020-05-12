package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl

import androidx.recyclerview.widget.RecyclerView
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.setup
import serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel.DashboardViewModel
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.DashboardAdapterDiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.categories.setupCategoriesWidgetBinding
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.header.setupHeaderWidgetBinding
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.moneyaccounts.setupMoneyAccountsWidgetBinding
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.transactions.setupRecentTransactionsBinding

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
fun RecyclerView.dashboard(viewModel: DashboardViewModel): DiffMultiViewAdapter<BaseCell> {
    return setup(DashboardAdapterDiffCallback()) {
        setupHeaderWidgetBinding(viewModel)
        setupCategoriesWidgetBinding(viewModel)
        setupRecentTransactionsBinding(viewModel)
        setupMoneyAccountsWidgetBinding(viewModel)
    }
}