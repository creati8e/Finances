package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import androidx.annotation.StringRes
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategorySharesCell
import serg.chuprin.finances.core.piechart.model.PieChartDataPart
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryShareCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardExpenseCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardIncomeCategoriesPageCell
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 24.04.2020.
 */
class DashboardCategoriesWidgetCellBuilder @Inject constructor(
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter,
    private val transitionNameBuilder: TransitionNameBuilder,
    private val categoryColorFormatter: CategoryColorFormatter
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.Categories) {
            return null
        }
        return DashboardWidgetCell.Categories(
            widget = widget,
            pageCells = widget.pages.map { page -> buildPageCell(page, widget.currency) }
        )
    }

    private fun buildPageCell(
        page: DashboardCategoriesWidgetPage,
        currency: Currency
    ): CategorySharesCell {
        val label = when (page.transactionType) {
            PlainTransactionType.INCOME -> resourceManger.getString(CoreR.string.income)
            PlainTransactionType.EXPENSE -> resourceManger.getString(CoreR.string.expenses)
        }
        if (page.transactionType == PlainTransactionType.INCOME) {
            return DashboardIncomeCategoriesPageCell(
                label = label,
                chartParts = buildChartParts(page),
                transactionType = page.transactionType,
                categoryCells = buildCategoryCells(page, currency, page.transactionType),
                totalAmount = amountFormatter.format(currency = currency, amount = page.totalAmount)
            )
        } else {
            return DashboardExpenseCategoriesPageCell(
                label = label,
                chartParts = buildChartParts(page),
                transactionType = page.transactionType,
                categoryCells = buildCategoryCells(page, currency, page.transactionType),
                totalAmount = amountFormatter.format(currency = currency, amount = page.totalAmount)
            )
        }
    }

    private fun buildCategoryCells(
        page: DashboardCategoriesWidgetPage,
        currency: Currency,
        transactionType: PlainTransactionType
    ): List<BaseCell> {
        if (page.categoryShares.isEmpty()) {
            return listOf(DashboardCategoriesPageZeroDataCell())
        }
        return page.categoryShares
            .mapTo(mutableListOf()) { (category, amount) ->
                val categoryName = category?.name ?: getString(CoreR.string.no_category)
                val chipText = "$categoryName ${amountFormatter.format(amount, currency)}"
                DashboardCategoryShareCell(
                    text = chipText,
                    category = category,
                    isOtherCategory = false,
                    plainTransactionType = transactionType,
                    colorInt = categoryColorFormatter.format(category),
                    transitionName = if (category?.id == null) {
                        transitionNameBuilder.buildForTransactionsReportUnknownCategory(
                            page.transactionType
                        )
                    } else {
                        transitionNameBuilder.buildForTransactionsReport(category.id)
                    }
                )
            }.apply {
                if (page.otherCategoriesShare != BigDecimal.ZERO) {
                    add(buildOtherCategoriesCell(page, currency))
                }
            }
    }

    private fun buildOtherCategoriesCell(
        page: DashboardCategoriesWidgetPage,
        currency: Currency
    ): DashboardCategoryShareCell {
        val name = getString(R.string.dashboard_categories_widget_other_categories)
        val formattedShare = amountFormatter.format(page.otherCategoriesShare, currency)
        return DashboardCategoryShareCell(
            category = null,
            isOtherCategory = true,
            text = "$name $formattedShare",
            colorInt = getOtherCategoriesColor(),
            plainTransactionType = page.transactionType,
            transitionName = transitionNameBuilder.buildForTransactionsReportOtherCategory(
                page.transactionType
            )
        )
    }

    private fun buildChartParts(page: DashboardCategoriesWidgetPage): List<PieChartDataPart> {
        if (page.categoryShares.isEmpty()) {
            return listOf(
                PieChartDataPart(
                    value = 1f,
                    colorInt = getEmptyChartPartColor()
                )
            )
        }
        return page.categoryShares.mapTo(mutableListOf()) { (category, amount) ->
            PieChartDataPart(
                value = amount.toFloat(),
                colorInt = categoryColorFormatter.format(category)
            )
        }.apply {
            if (page.otherCategoriesShare != BigDecimal.ZERO) {
                add(
                    PieChartDataPart(
                        colorInt = getOtherCategoriesColor(),
                        value = page.otherCategoriesShare.toFloat()
                    )
                )
            }
        }
    }

    private fun getString(@StringRes stringRes: Int): String {
        return resourceManger.getString(stringRes)
    }

    private fun getOtherCategoriesColor() = resourceManger.getColor(R.color.colorAccent)

    private fun getEmptyChartPartColor() = resourceManger.getColor(R.color.colorEmptyChartPart)

}