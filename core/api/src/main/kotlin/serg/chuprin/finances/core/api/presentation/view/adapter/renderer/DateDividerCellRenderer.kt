package serg.chuprin.finances.core.api.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_date_divider.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.model.cells.DateDividerCell

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class DateDividerCellRenderer : ContainerRenderer<DateDividerCell>() {

    override val type: Int = R.layout.cell_date_divider

    override fun bindView(viewHolder: ContainerHolder, cell: DateDividerCell) {
        viewHolder.textView.text = cell.dateFormatted
    }

}