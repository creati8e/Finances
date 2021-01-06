package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
sealed class UserProfileEffect {

    class CellsUpdated(
        val cells: List<BaseCell>
    ) : UserProfileEffect()

    object LoggedOut : UserProfileEffect()

}