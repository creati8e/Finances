package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesDataSource
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.impl.data.datasource.preferences.PreferencesDataSourceImpl

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
@Module
internal interface CorePreferencesModule {

    @[Binds AppScope]
    fun providePreferencesAdapter(impl: PreferencesDataSourceImpl): PreferencesDataSource

}