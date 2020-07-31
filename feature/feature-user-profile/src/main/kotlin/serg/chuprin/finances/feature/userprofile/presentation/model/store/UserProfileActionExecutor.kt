package serg.chuprin.finances.feature.userprofile.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.userprofile.presentation.model.builder.UserProfileCellsBuilder
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileActionExecutor @Inject constructor(
    private val cellsBuilder: UserProfileCellsBuilder
) : StoreActionExecutor<UserProfileAction, UserProfileState, UserProfileEffect, UserProfileEvent> {

    override fun invoke(
        action: UserProfileAction,
        state: UserProfileState,
        eventConsumer: Consumer<UserProfileEvent>,
        actionsFlow: Flow<UserProfileAction>
    ): Flow<UserProfileEffect> {
        return when (action) {
            is UserProfileAction.UpdateUser -> {
                handleUpdateUserAction(action)
            }
            is UserProfileAction.ExecuteIntent -> TODO()
        }
    }

    private fun handleUpdateUserAction(
        action: UserProfileAction.UpdateUser
    ): Flow<UserProfileEffect> {
        return flowOfSingleValue {
            UserProfileEffect.CellsUpdated(cellsBuilder.build(action.user))
        }
    }

}