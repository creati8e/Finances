package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import androidx.annotation.StringRes
import serg.chuprin.finances.core.api.domain.model.orUnknown
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.DataPeriodUi
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryShareCell
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardState
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class DashboardTransactionsReportArgumentsBuilder @Inject constructor(
    private val resourceManger: ResourceManger
) {

    fun buildForAllTransactions(
        state: DashboardState
    ): TransactionsReportScreenArguments.AllTransactions {
        return TransactionsReportScreenArguments.AllTransactions(
            dataPeriodUi = DataPeriodUi.create(state.dashboard.currentDataPeriod),
            transitionName = resourceManger.getString(
                R.string.transition_dashboard_recent_transactions_to_transactions_report
            )
        )
    }

    fun buildForPeriodExpenses(
        state: DashboardState
    ): TransactionsReportScreenArguments.Transactions {
        return buildForPeriodTransactions(
            dataPeriod = state.dashboard.currentDataPeriod,
            transactionType = PlainTransactionType.EXPENSE,
            transitionNameStringRes = R.string.transition_dashboard_to_transactions_report_expenses
        )
    }

    fun buildForPeriodIncome(
        state: DashboardState
    ): TransactionsReportScreenArguments.Transactions {
        return buildForPeriodTransactions(
            dataPeriod = state.dashboard.currentDataPeriod,
            transactionType = PlainTransactionType.INCOME,
            transitionNameStringRes = R.string.transition_dashboard_to_transactions_report_income
        )
    }

    fun buildForCategory(
        categoryCell: DashboardCategoryShareCell,
        state: DashboardState
    ): TransactionsReportScreenArguments {
        val dataPeriod = DataPeriodUi.create(state.dashboard.currentDataPeriod)

        if (categoryCell.isOtherCategory) {
            val categoryIds = getCategoriesPage(state, categoryCell)
                .otherCategoryShares
                .orEmpty()
                .mapNotNullTo(ArrayList(), { (category) -> category?.id?.value })

            return TransactionsReportScreenArguments.Category.Other(
                dataPeriodUi = dataPeriod,
                categoryIds = categoryIds,
                transitionName = categoryCell.transitionName,
                transactionType = categoryCell.plainTransactionType
            )
        }
        return TransactionsReportScreenArguments.Category.Concrete(
            dataPeriodUi = dataPeriod,
            transitionName = categoryCell.transitionName,
            categoryId = categoryCell.category?.id.orUnknown(),
            transactionType = categoryCell.plainTransactionType
        )
    }

    private fun buildForPeriodTransactions(
        dataPeriod: DataPeriod,
        transactionType: PlainTransactionType,
        @StringRes transitionNameStringRes: Int
    ): TransactionsReportScreenArguments.Transactions {
        return TransactionsReportScreenArguments.Transactions(
            transactionType = transactionType,
            dataPeriodUi = DataPeriodUi.create(dataPeriod),
            transitionName = resourceManger.getString(transitionNameStringRes)
        )
    }

    private fun getCategoriesPage(
        state: DashboardState,
        categoryCell: DashboardCategoryShareCell
    ): DashboardCategoriesWidgetPage {
        val key = DashboardWidgetType.CATEGORIES
        val widget = (state.dashboard.widgets.getValue(key) as DashboardWidget.Categories)
        return widget.pages.first { it.transactionType == categoryCell.plainTransactionType }
    }

}