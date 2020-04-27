package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import serg.chuprin.finances.core.api.di.Initializer
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.firebase.FirebaseInitializer
import serg.chuprin.finances.core.impl.BuildConfig
import serg.chuprin.finances.core.impl.di.initializer.AppInitializer
import serg.chuprin.finances.core.impl.di.initializer.TimberInitializer
import serg.chuprin.finances.core.impl.presentation.debug.AppDebugMenuImpl
import serg.chuprin.finances.core.impl.presentation.debug.AppDebugMenuNoOnImpl
import javax.inject.Provider

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
@Module
internal interface CoreInitializersModule {

    @Module
    companion object {

        @[Provides IntoSet]
        fun provideFirebaseInitializer(): Initializer = FirebaseInitializer()

        @[Provides IntoSet]
        fun provideDebugMenu(resourceManger: Provider<ResourceManger>): Initializer {
            return if (BuildConfig.DEBUG) {
                AppDebugMenuImpl(resourceManger.get())
            } else {
                AppDebugMenuNoOnImpl()
            }
        }
    }

    @Binds
    fun bindAppInitializer(initializer: AppInitializer): Initializer

    @[Binds IntoSet]
    fun bindTimberInitializer(initializer: TimberInitializer): Initializer

}