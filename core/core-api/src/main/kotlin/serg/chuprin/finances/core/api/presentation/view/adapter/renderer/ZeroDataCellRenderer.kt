package serg.chuprin.finances.core.api.presentation.view.adapter.renderer

import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
import kotlinx.android.synthetic.main.cell_zero_data.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.view.extensions.adjustHeightToFillParent

/**
 * Created by Sergey Chuprin on 21.03.2019.
 */
class ZeroDataCellRenderer : ContainerRenderer<ZeroDataCell>() {

    override val type: Int = serg.chuprin.finances.core.api.R.layout.cell_zero_data

    override fun bindView(holder: ContainerHolder, model: ZeroDataCell) {
        holder.zeroDataView.setup(
            iconRes = model.iconRes,
            titleRes = model.titleRes,
            contentMessageRes = model.contentMessageRes
        )

        if (model.fillParent) {
            holder.itemView.doOnPreDraw { view ->
                view.adjustHeightToFillParent()
            }
        } else {
            holder.itemView.updateLayoutParams<ViewGroup.LayoutParams> {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
    }

}