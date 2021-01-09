package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
@ScreenScope
class UserProfileStoreFactory @Inject constructor(
    actionExecutor: UserProfileActionExecutor,
    bootstrapper: UserProfileBootstrapper
) : AbsStoreFactory<UserProfileIntent, UserProfileEffect, UserProfileAction, UserProfileState, UserProfileEvent, UserProfileStore>(
    UserProfileState(),
    UserProfileStateReducer(),
    bootstrapper,
    actionExecutor,
    UserProfileAction::ExecuteIntent
)