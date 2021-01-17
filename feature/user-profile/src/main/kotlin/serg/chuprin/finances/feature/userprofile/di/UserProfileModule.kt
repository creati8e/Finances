package serg.chuprin.finances.feature.userprofile.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileStore
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileStoreFactory

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
@Module
object UserProfileModule {

    @[Provides ScreenScope]
    fun provideStore(factory: UserProfileStoreFactory): UserProfileStore = factory.create()

}