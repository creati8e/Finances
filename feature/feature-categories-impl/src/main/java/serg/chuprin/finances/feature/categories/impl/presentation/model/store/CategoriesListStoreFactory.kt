package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListStoreFactory @Inject constructor(
    executor: CategoriesListActionExecutor,
    bootstrapper: CategoriesListStoreBootstrapper,
) : AbsStoreFactory<CategoriesListIntent, CategoriesListEffect, CategoriesListAction, CategoriesListState, CategoriesListEvent, CategoriesListStore>(
    CategoriesListState(),
    CategoriesListStateReducer(),
    bootstrapper,
    executor,
    CategoriesListAction::ExecuteIntent
)