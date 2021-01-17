package serg.chuprin.finances.feature.categories.list.presentation.model.store

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.extensions.flow.takeUntil
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.categories.list.R
import serg.chuprin.finances.feature.categories.list.domain.usecase.SearchUserCategoriesUseCase
import serg.chuprin.finances.feature.categories.list.presentation.model.cell.ChildCategoryCell
import serg.chuprin.finances.feature.categories.list.presentation.model.cell.ParentCategoryCell
import serg.chuprin.finances.feature.categories.list.presentation.model.expansion.CategoryListExpansionTracker
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListActionExecutor @Inject constructor(
    private val screenArguments: CategoriesListScreenArguments,
    private val expansionTracker: CategoryListExpansionTracker,
    private val categoryColorFormatter: CategoryColorFormatter,
    private val searchUserCategoriesUseCase: SearchUserCategoriesUseCase
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
                    is CategoriesListIntent.EnterSearchQuery -> {
                        handleEnterSearchQueryIntent(intent, actionsFlow)
                    }
                    is CategoriesListIntent.ClickOnCategoryCell -> {
                        handleClickOnCategoryCell(intent, eventConsumer)
                    }
                }
            }
            is CategoriesListAction.BuildCategoriesList -> {
                handleBuildCategoriesListAction(action)
            }
        }
    }

    private fun handleClickOnCategoryCell(
        intent: CategoriesListIntent.ClickOnCategoryCell,
        eventConsumer: Consumer<CategoriesListEvent>
    ): Flow<CategoriesListEffect> {
        return emptyFlowAction {
            if (screenArguments.isInPickerMode()) {
                val categoryId = intent.categoryCell.category.id
                eventConsumer(CategoriesListEvent.CloseScreenWithPickerCategory(categoryId))
            }
        }
    }

    private fun handleEnterSearchQueryIntent(
        intent: CategoriesListIntent.EnterSearchQuery,
        actionsFlow: Flow<CategoriesListAction>
    ): Flow<CategoriesListEffect> {
        return flowOfSingleValue {
            delay(300)
            val categories = searchUserCategoriesUseCase
                .execute(
                    nameQuery = intent.query,
                    categoryType = screenArguments.categoryType
                )
                .first()
            CategoriesListEffect.CellsBuilt(buildCellsForSearch(categories))
        }.takeUntil(actionsFlow.filter { action ->
            action is CategoriesListAction.ExecuteIntent
                    && action.intent is CategoriesListIntent.EnterSearchQuery
        })
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
                    val category = categoryWithChildren.category
                    add(
                        ParentCategoryCell(
                            category = category,
                            isExpanded = expansions.containsKey(category.id),
                            color = categoryColorFormatter.format(category),
                            isExpansionAvailable = categoryWithChildren.children.isNotEmpty()
                        )
                    )
                    if (category.id in expansions) {
                        categoryWithChildren.children.forEach { childCategory ->
                            add(ChildCategoryCell(category = childCategory))
                        }
                    }
                }
            }
            CategoriesListEffect.CellsBuilt(cells)
        }
    }

    private fun buildCellsForSearch(categories: Collection<Category>): List<BaseCell> {
        if (categories.isEmpty()) {
            return listOf(
                ZeroDataCell(
                    iconRes = null,
                    buttonRes = null,
                    buttonTransitionName = EMPTY_STRING,
                    titleRes = R.string.categories_list_search_zero_data_title,
                    contentMessageRes = R.string.categories_list_search_zero_data_message
                )
            )
        }
        return categories.map { category ->
            ParentCategoryCell(
                category = category,
                isExpanded = false,
                isExpansionAvailable = false,
                color = categoryColorFormatter.format(category)
            )
        }
    }

}