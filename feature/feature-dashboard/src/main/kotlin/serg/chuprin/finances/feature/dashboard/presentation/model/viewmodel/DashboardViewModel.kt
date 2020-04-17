package serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardState
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardViewModel @Inject constructor(
    private val store: DashboardStore
) : BaseStoreViewModel<DashboardIntent>() {

    val userPhotoLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.user.photoUrl }

    val cellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData(DashboardState::cells)

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}