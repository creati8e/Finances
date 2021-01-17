package serg.chuprin.finances.feature.categories.list.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
typealias CategoriesListStore = StateStore<CategoriesListIntent, CategoriesListState, CategoriesListEvent>