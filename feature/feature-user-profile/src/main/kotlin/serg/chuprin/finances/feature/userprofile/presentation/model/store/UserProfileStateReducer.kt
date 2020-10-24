package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileStateReducer : StoreStateReducer<UserProfileEffect, UserProfileState> {

    override fun invoke(what: UserProfileEffect, state: UserProfileState): UserProfileState {
        return when (what) {
            is UserProfileEffect.CellsUpdated -> {
                state.copy(cells = what.cells)
            }
            UserProfileEffect.LoggedOut -> state
        }
    }

}