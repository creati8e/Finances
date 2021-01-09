package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileStore @Inject constructor(
    actionExecutor: UserProfileActionExecutor,
    bootstrapper: UserProfileBootstrapper
) : BaseStateStore<UserProfileIntent, UserProfileEffect, UserProfileAction, UserProfileState, UserProfileEvent>(
    UserProfileState(),
    UserProfileStateReducer(),
    bootstrapper,
    actionExecutor,
    UserProfileAction::ExecuteIntent
)