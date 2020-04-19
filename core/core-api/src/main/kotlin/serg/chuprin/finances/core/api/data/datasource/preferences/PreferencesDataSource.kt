package serg.chuprin.finances.core.api.data.datasource.preferences

import androidx.annotation.CheckResult
import serg.chuprin.finances.core.api.data.datasource.preferences.mapper.PreferenceMapper

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
interface PreferencesDataSource {

    @CheckResult
    fun getBoolean(key: String, defaultValue: Boolean): Preference<Boolean>

    @CheckResult
    fun getFloat(key: String, defaultValue: Float): Preference<Float>

    @CheckResult
    fun getInt(key: String, defaultValue: Int): Preference<Int>

    @CheckResult
    fun getLong(key: String, defaultValue: Long): Preference<Long>

    @CheckResult
    fun getString(key: String, defaultValue: String): Preference<String>

    @CheckResult
    fun <M> getCustomModel(key: String, mapper: PreferenceMapper<M>): Preference<M>

    fun clear()

}