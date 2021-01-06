package serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions

import androidx.lifecycle.ViewModel

/**
 * Created by Sergey Chuprin on 31.01.2019.
 *
 * ViewModel which holds Dagger's component and let it survive across configuration changes.
 */
class ComponentViewModel<C>(val component: C) : ViewModel() {

    private var _component: C? = component

    override fun onCleared() {
        super.onCleared()
        _component = null
    }

}