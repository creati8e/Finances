package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.categories.impl.domain.usecase.GetAllUserCategoriesUseCase
import serg.chuprin.finances.feature.categories.impl.presentation.model.expansion.CategoryListExpansionTracker
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListStoreBootstrapper @Inject constructor(
    private val useCase: GetAllUserCategoriesUseCase,
    private val expansionTracker: CategoryListExpansionTracker
) : StoreBootstrapper<CategoriesListAction> {

    override fun invoke(): Flow<CategoriesListAction> {
        return flow {
            emitAll(
                combine(
                    useCase.execute(),
                    expansionTracker.expansionsFlow,
                    CategoriesListAction::BuildCategoriesList
                )
            )
        }
    }

}