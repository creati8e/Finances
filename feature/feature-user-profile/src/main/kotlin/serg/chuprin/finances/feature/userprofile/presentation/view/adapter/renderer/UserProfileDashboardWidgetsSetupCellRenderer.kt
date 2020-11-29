package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_user_profile_setup_dashboard_widgets.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileSetupDashboardWidgetsCell

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class UserProfileDashboardWidgetsSetupCellRenderer :
    ContainerRenderer<UserProfileSetupDashboardWidgetsCell>() {

    override val type: Int = R.layout.cell_user_profile_setup_dashboard_widgets

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            dashboardWidgetsSetupLayout.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

}