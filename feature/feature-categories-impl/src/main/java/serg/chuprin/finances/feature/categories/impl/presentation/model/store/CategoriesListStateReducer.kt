package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoriesListStateReducer : StoreStateReducer<CategoriesListEffect, CategoriesListState> {

    override fun invoke(
        what: CategoriesListEffect,
        state: CategoriesListState
    ): CategoriesListState {
        return when (what) {
            is CategoriesListEffect.CellsBuilt -> {
                when (state) {
                    is CategoriesListState.AllCategories -> {
                        state.copy(cells = what.cells)
                    }
                    is CategoriesListState.Search -> {
                        state.copy(cells = what.cells)
                    }
                }
            }
        }
    }

}