package serg.chuprin.finances.feature.categories.list.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithChildren

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListAction {

    class ExecuteIntent(
        val intent: CategoriesListIntent
    ) : CategoriesListAction()

    class BuildCategoriesList(
        val categories: Set<CategoryWithChildren>,
        val expansions: Map<Id, Boolean>
    ) : CategoriesListAction()

}