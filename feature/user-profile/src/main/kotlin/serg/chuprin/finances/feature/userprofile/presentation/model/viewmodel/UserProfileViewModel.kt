package serg.chuprin.finances.feature.userprofile.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileEvent
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileIntent
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileState
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
@ScreenScope
class UserProfileViewModel @Inject constructor(
    store: UserProfileStore
) : BaseStoreViewModel<UserProfileIntent>() {

    val cellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData(UserProfileState::cells)

    val eventsLiveData: LiveData<UserProfileEvent> = store.observeEventsAsLiveData()

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}