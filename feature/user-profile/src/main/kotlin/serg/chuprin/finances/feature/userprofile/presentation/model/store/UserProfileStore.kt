package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
typealias UserProfileStore = StateStore<UserProfileIntent, UserProfileState, UserProfileEvent>