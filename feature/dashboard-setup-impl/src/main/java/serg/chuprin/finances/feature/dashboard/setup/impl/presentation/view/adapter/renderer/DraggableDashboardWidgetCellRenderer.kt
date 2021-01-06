package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_draggable_dashboard_widget.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.doIgnoringChanges
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.extensions.shouldIgnoreChanges
import serg.chuprin.finances.feature.dashboard.setup.impl.R
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells.DraggableDashboardWidgetCell

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class DraggableDashboardWidgetCellRenderer(
    private val onCheckedChanged: (adapterPosition: Int) -> Unit
) : ContainerRenderer<DraggableDashboardWidgetCell>() {

    override val type: Int = R.layout.cell_draggable_dashboard_widget

    override fun bindView(holder: ContainerHolder, model: DraggableDashboardWidgetCell) {
        with(holder) {
            with(checkBox) {
                doIgnoringChanges {
                    isChecked = model.isChecked
                }
            }
            widgetNameTextView.text = model.name
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        super.onVhCreated(holder, clickListener, longClickListener)
        with(holder) {
            itemView.onClick {
                onCheckedChanged(adapterPosition)
            }
            checkBox.setOnCheckedChangeListener { buttonView, _ ->
                if (buttonView.shouldIgnoreChanges.not()) {
                    onCheckedChanged(adapterPosition)
                }
            }
        }
    }

}