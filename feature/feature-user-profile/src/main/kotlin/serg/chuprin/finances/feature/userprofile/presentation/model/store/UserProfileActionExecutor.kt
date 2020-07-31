package serg.chuprin.finances.feature.userprofile.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileActionExecutor :
    StoreActionExecutor<UserProfileAction, UserProfileState, UserProfileEffect, UserProfileEvent> {

    override fun invoke(
        action: UserProfileAction,
        state: UserProfileState,
        eventConsumer: Consumer<UserProfileEvent>,
        actionsFlow: Flow<UserProfileAction>
    ): Flow<UserProfileEffect> {
        TODO("Not yet implemented")
    }

}