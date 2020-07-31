package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
data class UserProfileState(
    val cells: List<BaseCell> = emptyList()
)