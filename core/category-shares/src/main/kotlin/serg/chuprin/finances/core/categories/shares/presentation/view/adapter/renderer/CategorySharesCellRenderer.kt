package serg.chuprin.finances.core.categories.shares.presentation.view.adapter.renderer

import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.view_category_shares.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategoryShareCell
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategorySharesCell
import serg.chuprin.finances.core.categories.shares.presentation.view.adapter.diff.CategoryChipCellsAdapterDiffCallback
import serg.chuprin.finances.core.piechart.model.PieChartData

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class CategorySharesCellRenderer<T : CategoryShareCell>(
    override val type: Int,
    private val categoryChipCellClass: Class<T>,
    private val onCategoryClicked: (cell: T) -> Unit,
    private val categoryChipsAdapterSetup: (DiffMultiViewAdapter<BaseCell>.() -> Unit)? = null
) : ContainerRenderer<CategorySharesCell>() {

    private val categoryCellsAdapter = DiffMultiViewAdapter(
        CategoryChipCellsAdapterDiffCallback()
    ).apply {
        categoryChipsAdapterSetup?.invoke(this)
        registerRenderer(
            CategoryShareCellRenderer(::handleOnCategoryChipClick),
            categoryChipCellClass
        )
    }

    override fun bindView(viewHolder: ContainerHolder, cell: CategorySharesCell) {
        bindData(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: CategorySharesCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<CategorySharesCell.ChangedPayload>()) {
            bindData(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder.categoryChipsRecyclerView) {
            adapter = categoryCellsAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                justifyContent = JustifyContent.CENTER
            }
        }
    }

    private fun bindData(
        viewHolder: ContainerHolder,
        cell: CategorySharesCell
    ) {
        categoryCellsAdapter.setItems(cell.categoryCells)
        viewHolder.pieChart.setData(
            animate = false,
            secondaryText = cell.label,
            primaryText = cell.totalAmount,
            pieChartData = PieChartData(cell.chartParts)
        )
    }

    private fun handleOnCategoryChipClick(adapterPosition: Int) {
        val itemOrNull = categoryCellsAdapter.getItemOrNull(adapterPosition)
        val cell = itemOrNull as? CategoryShareCell ?: return

        @Suppress("UNCHECKED_CAST")
        onCategoryClicked(cell as T)
    }

}