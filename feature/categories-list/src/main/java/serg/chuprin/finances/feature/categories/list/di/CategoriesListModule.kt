package serg.chuprin.finances.feature.categories.list.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.categories.list.presentation.model.expansion.CategoryListExpansionTracker
import serg.chuprin.finances.feature.categories.list.presentation.model.store.CategoriesListStore
import serg.chuprin.finances.feature.categories.list.presentation.model.store.CategoriesListStoreFactory

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
@Module
object CategoriesListModule {

    @[Provides ScreenScope]
    fun provideStore(factory: CategoriesListStoreFactory): CategoriesListStore = factory.create()

    @[Provides ScreenScope]
    fun provideExpansionTracker(): CategoryListExpansionTracker = CategoryListExpansionTracker()

}