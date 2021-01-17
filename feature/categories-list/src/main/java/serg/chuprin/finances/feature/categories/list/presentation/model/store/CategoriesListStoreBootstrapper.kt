package serg.chuprin.finances.feature.categories.list.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.categories.list.domain.usecase.GetAllUserCategoriesUseCase
import serg.chuprin.finances.feature.categories.list.presentation.model.expansion.CategoryListExpansionTracker
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListStoreBootstrapper @Inject constructor(
    private val useCase: GetAllUserCategoriesUseCase,
    private val screenArguments: CategoriesListScreenArguments,
    private val expansionTracker: CategoryListExpansionTracker
) : StoreBootstrapper<CategoriesListAction> {

    override fun invoke(): Flow<CategoriesListAction> {
        return combine(
            useCase.execute(screenArguments.categoryType),
            expansionTracker.expansionsFlow,
            CategoriesListAction::BuildCategoriesList
        )
    }

}