package serg.chuprin.finances.feature.categories.impl.presentation.model.viewmodel

import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.categories.impl.presentation.model.store.CategoriesListIntent
import serg.chuprin.finances.feature.categories.impl.presentation.model.store.CategoriesListStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListViewModel @Inject constructor(
    store: CategoriesListStore
) : BaseStoreViewModel<CategoriesListIntent>() {

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}