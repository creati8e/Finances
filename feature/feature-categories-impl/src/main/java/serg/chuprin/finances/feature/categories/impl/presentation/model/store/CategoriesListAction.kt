package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithChildren

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListAction {

    class ExecuteIntent(
        val intent: CategoriesListIntent
    ) : CategoriesListAction()

    class BuildCategoriesList(
        val categories: Set<TransactionCategoryWithChildren>
    ) : CategoriesListAction()

}