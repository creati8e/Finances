package serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories

import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategoryChipCell

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
data class DashboardCategoryChipCell(
    override val colorInt: Int,
    override val chipText: String,
    override val transitionName: String,
    val isOtherCategory: Boolean,
    override val category: Category?,
    override val plainTransactionType: PlainTransactionType
) : CategoryChipCell