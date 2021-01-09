package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_user_profile_data_period_type.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileDataPeriodTypeCell
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.diff.UserProfilePeriodChangedDiffPayload

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileDataPeriodTypeCellRenderer : ContainerRenderer<UserProfileDataPeriodTypeCell>() {

    override val type: Int = R.layout.cell_user_profile_data_period_type

    override fun bindView(viewHolder: ContainerHolder, cell: UserProfileDataPeriodTypeCell) {
        bind(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: UserProfileDataPeriodTypeCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.any { it is UserProfilePeriodChangedDiffPayload }) {
            bind(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

    private fun bind(
        viewHolder: ContainerHolder,
        cell: UserProfileDataPeriodTypeCell
    ) {
        viewHolder.dataPeriodTypeValueTextView.text = cell.periodTypeDisplayName
    }

}