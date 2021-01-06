package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesDataSource

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
interface CoreDataSourceProvider {

    val preferencesDataSource: PreferencesDataSource

}