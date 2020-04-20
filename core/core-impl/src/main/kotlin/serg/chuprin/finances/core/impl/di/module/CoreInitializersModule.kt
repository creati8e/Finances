package serg.chuprin.finances.core.impl.di.module

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import serg.chuprin.finances.core.api.di.Initializer
import serg.chuprin.finances.core.firebase.FirebaseInitializer
import serg.chuprin.finances.core.impl.di.initializer.AppInitializer
import serg.chuprin.finances.core.impl.di.initializer.TimberInitializer

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
@Module
internal object CoreInitializersModule {

    @Provides
    fun provideAppInitializer(initializers: Set<@JvmSuppressWildcards Initializer>): Initializer {
        return AppInitializer(initializers)
    }

    @Provides
    @IntoSet
    fun provideTimberInitializer(): Initializer = TimberInitializer()

    @Provides
    @IntoSet
    fun provideFirebaseInitializer(): Initializer = FirebaseInitializer()

}