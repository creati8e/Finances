package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
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
        return combine(
            useCase.execute(),
            expansionTracker.expansionsFlow.onEach {
                Timber.d { "Emit data expansionsFlow" }
            },
            CategoriesListAction::BuildCategoriesList
        )
    }

}