package serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories

import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategoryShareCell

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
data class DashboardCategoryShareCell(
    override val colorInt: Int,
    override val text: String,
    override val transitionName: String,
    val isOtherCategory: Boolean,
    override val category: Category?,
    override val plainTransactionType: PlainTransactionType
) : CategoryShareCell