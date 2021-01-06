package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.SmartDiffCallback
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.*

/**
 * Created by Sergey Chuprin on 06.12.2020.
 */
class UserProfileDiffCallback : SmartDiffCallback<BaseCell>() {

    init {
        addItemsComparator<UserProfileDataPeriodTypeCell> { _, _ -> true }

        addContentsComparator(UserProfileImageCell::equals)
        addContentsComparator(UserProfileDataPeriodTypeCell::equals)
        addContentsComparator<UserProfileLogoutCell> { _, _ -> true }
        addContentsComparator<UserProfileCategoriesCell> { _, _ -> true }
        addContentsComparator<UserProfileSetupDashboardWidgetsCell> { _, _ -> true }

        addPayloadProvider<UserProfileDataPeriodTypeCell> { _, _ ->
            UserProfilePeriodChangedDiffPayload()
        }
        addPayloadProvider<UserProfileImageCell> { _, _ ->
            UserProfileChangedDiffPayload()
        }
    }

}