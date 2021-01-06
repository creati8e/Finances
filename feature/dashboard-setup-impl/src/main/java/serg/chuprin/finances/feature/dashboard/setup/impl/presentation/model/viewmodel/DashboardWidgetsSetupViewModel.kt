package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells.DraggableDashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupEvent
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupIntent
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupState
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
@ScreenScope
class DashboardWidgetsSetupViewModel @Inject constructor(
    private val store: DashboardWidgetsSetupStore
) : BaseStoreViewModel<DashboardWidgetsSetupIntent>() {

    val saveButtonEnabled: Boolean
        get() = store.state.hasChanges

    val cellsLiveData: LiveData<Pair<List<DraggableDashboardWidgetCell>, Boolean>> =
        store.observeParticularStateAsLiveData { state ->
            state.widgetCells to state.scrollToMovedWidget
        }

    val saveButtonEnabledLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData(DashboardWidgetsSetupState::hasChanges)

    val eventsLiveData: LiveData<DashboardWidgetsSetupEvent> = store.observeEventsAsLiveData()

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}