package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListActionExecutor @Inject constructor() :
    StoreActionExecutor<CategoriesListAction, CategoriesListState, CategoriesListEffect, CategoriesListEvent> {

    override fun invoke(
        action: CategoriesListAction,
        state: CategoriesListState,
        eventConsumer: Consumer<CategoriesListEvent>,
        actionsFlow: Flow<CategoriesListAction>
    ): Flow<CategoriesListEffect> {
        TODO("Not yet implemented")
    }

}