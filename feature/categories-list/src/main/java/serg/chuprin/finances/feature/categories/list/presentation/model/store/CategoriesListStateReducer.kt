package serg.chuprin.finances.feature.categories.list.presentation.model.store

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
                        state.copy(allCells = what.cells)
                    }
                    is CategoriesListState.Search -> {
                        state.copy(searchCells = what.cells)
                    }
                }
            }
            is CategoriesListEffect.EnteredInSearchMode -> {
                if (state is CategoriesListState.AllCategories) {
                    CategoriesListState.Search(
                        allCells = what.allCells,
                        searchCells = what.searchCells
                    )
                } else {
                    state
                }
            }
            is CategoriesListEffect.ExitFromSearchMode -> {
                if (state is CategoriesListState.Search) {
                    CategoriesListState.AllCategories(allCells = what.allCells)
                } else {
                    state
                }
            }
        }
    }

}