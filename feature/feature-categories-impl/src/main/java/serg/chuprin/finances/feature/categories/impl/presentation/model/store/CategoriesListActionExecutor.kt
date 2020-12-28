package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ChildCategoryCell
import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ParentCategoryCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListActionExecutor @Inject constructor(
    private val categoryColorFormatter: CategoryColorFormatter
) : StoreActionExecutor<CategoriesListAction, CategoriesListState, CategoriesListEffect, CategoriesListEvent> {

    override fun invoke(
        action: CategoriesListAction,
        state: CategoriesListState,
        eventConsumer: Consumer<CategoriesListEvent>,
        actionsFlow: Flow<CategoriesListAction>
    ): Flow<CategoriesListEffect> {
        return when (action) {
            is CategoriesListAction.ExecuteIntent -> TODO()
            is CategoriesListAction.BuildCategoriesList -> {
                handleBuildCategoriesListAction(action)
            }
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
                            color = categoryColorFormatter.format(categoryWithChildren.category)
                        )
                    )
                    if (categoryWithChildren.category.id in expansions) {
                        categoryWithChildren.children.forEach { childCategory ->
                            add(
                                ChildCategoryCell(
                                    category = childCategory,
                                    color = categoryColorFormatter.format(childCategory)
                                )
                            )
                        }
                    }
                }
            }
            CategoriesListEffect.CellsBuilt(cells)
        }
    }

}