package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.feature.categories.impl.R
import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ChildCategoryCell
import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ParentCategoryCell
import serg.chuprin.finances.feature.categories.impl.presentation.model.expansion.CategoryListExpansionTracker
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListActionExecutor @Inject constructor(
    private val expansionTracker: CategoryListExpansionTracker,
    private val categoryColorFormatter: CategoryColorFormatter
) : StoreActionExecutor<CategoriesListAction, CategoriesListState, CategoriesListEffect, CategoriesListEvent> {

    override fun invoke(
        action: CategoriesListAction,
        state: CategoriesListState,
        eventConsumer: Consumer<CategoriesListEvent>,
        actionsFlow: Flow<CategoriesListAction>
    ): Flow<CategoriesListEffect> {
        return when (action) {
            is CategoriesListAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    is CategoriesListIntent.ClickOnParentCategoryExpansionToggle -> {
                        handleClickOnParentCategoryExpansionToggleIntent(intent)
                    }
                    CategoriesListIntent.ClickOnSearchIcon -> {
                        handleClickOnSearchIconIntent(state)
                    }
                    CategoriesListIntent.ClickOnCloseSearchIcon -> {
                        handleClickOnCloseSearchIconIntent(state)
                    }
                }
            }
            is CategoriesListAction.BuildCategoriesList -> {
                handleBuildCategoriesListAction(action)
            }
        }
    }

    private fun handleClickOnCloseSearchIconIntent(
        state: CategoriesListState
    ): Flow<CategoriesListEffect> {
        if (state is CategoriesListState.Search) {
            return flowOfSingleValue {
                CategoriesListEffect.ExitFromSearchMode(
                    allCells = state.allCells
                )
            }
        }
        return emptyFlow()
    }

    private fun handleClickOnSearchIconIntent(
        state: CategoriesListState
    ): Flow<CategoriesListEffect> {
        if (state is CategoriesListState.AllCategories) {
            return flowOfSingleValue {
                CategoriesListEffect.EnteredInSearchMode(
                    searchCells = listOf(
                        ZeroDataCell(
                            iconRes = null,
                            buttonRes = null,
                            contentMessageRes = null,
                            buttonTransitionName = EMPTY_STRING,
                            titleRes = R.string.categories_list_search_initial_zero_data_title
                        )
                    ),
                    allCells = state.allCells
                )
            }
        }
        return emptyFlow()
    }

    private fun handleClickOnParentCategoryExpansionToggleIntent(
        intent: CategoriesListIntent.ClickOnParentCategoryExpansionToggle
    ): Flow<CategoriesListEffect> {
        return emptyFlowAction {
            expansionTracker.toggleExpansion(intent.parentCategoryCell.category.id)
        }
    }

    private fun handleBuildCategoriesListAction(
        action: CategoriesListAction.BuildCategoriesList
    ): Flow<CategoriesListEffect> {
        val expansions = action.expansions
        return flowOfSingleValue {
            val cells = buildList<BaseCell> {
                action.categories.forEach { categoryWithChildren ->
                    add(
                        ParentCategoryCell(
                            category = categoryWithChildren.category,
                            isExpansionAvailable = categoryWithChildren.children.isNotEmpty(),
                            color = categoryColorFormatter.format(categoryWithChildren.category)
                        )
                    )
                    if (categoryWithChildren.category.id in expansions) {
                        categoryWithChildren.children.forEach { childCategory ->
                            add(ChildCategoryCell(category = childCategory))
                        }
                    }
                }
            }
            CategoriesListEffect.CellsBuilt(cells)
        }
    }

}