package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_user_profile_data_period_type.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
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

    override fun bindView(holder: ContainerHolder, model: UserProfileDataPeriodTypeCell) {
        bind(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: UserProfileDataPeriodTypeCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.any { it is UserProfilePeriodChangedDiffPayload }) {
            bind(holder, model)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

    private fun bind(
        holder: ContainerHolder,
        model: UserProfileDataPeriodTypeCell
    ) {
        holder.dataPeriodTypeValueTextView.text = model.periodTypeDisplayName
    }

}