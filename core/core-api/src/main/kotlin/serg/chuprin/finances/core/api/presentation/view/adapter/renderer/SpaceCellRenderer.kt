package serg.chuprin.finances.core.api.presentation.view.adapter.renderer

import androidx.core.view.updateLayoutParams
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.model.cells.SpaceCell
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
class SpaceCellRenderer : ContainerRenderer<SpaceCell>() {

    override val type: Int = R.layout.cell_space

    override fun bindView(holder: ContainerHolder, model: SpaceCell) {
        with(holder.itemView) {
            updateLayoutParams {
                height = context.dpToPx(model.sizeDp)
            }
        }
    }

}