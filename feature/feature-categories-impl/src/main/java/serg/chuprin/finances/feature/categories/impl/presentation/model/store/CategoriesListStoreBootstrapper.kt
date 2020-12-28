package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@ScreenScope
class CategoriesListStoreBootstrapper
@Inject constructor() : StoreBootstrapper<CategoriesListAction> {

    override fun invoke(): Flow<CategoriesListAction> {
        TODO("Not yet implemented")
    }

}