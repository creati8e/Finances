package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesAdapter
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.impl.data.preferences.adapter.PreferencesAdapterImpl

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
@Module
internal abstract class CorePreferencesModule {

    @[Binds AppScope]
    abstract fun providePreferencesAdapter(impl: PreferencesAdapterImpl): PreferencesAdapter

}