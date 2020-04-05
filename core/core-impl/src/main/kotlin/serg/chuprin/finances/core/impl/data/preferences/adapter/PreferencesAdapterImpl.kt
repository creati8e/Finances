package serg.chuprin.finances.core.impl.data.preferences.adapter

import android.content.SharedPreferences
import com.afollestad.rxkprefs.rxkPrefs
import serg.chuprin.finances.core.api.data.datasource.preferences.Preference
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesAdapter
import serg.chuprin.finances.core.api.data.datasource.preferences.mapper.PreferenceMapper
import serg.chuprin.finances.core.impl.data.preferences.CustomModelPreferenceImpl
import serg.chuprin.finances.core.impl.data.preferences.PreferenceImpl
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */
internal class PreferencesAdapterImpl @Inject constructor(
    sharedPreferences: SharedPreferences
) : PreferencesAdapter {

    private val rxkPrefs = rxkPrefs(sharedPreferences)

    override fun getBoolean(key: String, defaultValue: Boolean): Preference<Boolean> {
        return PreferenceImpl(rxkPrefs.boolean(key, defaultValue))
    }

    override fun getFloat(key: String, defaultValue: Float): Preference<Float> {
        return PreferenceImpl(rxkPrefs.float(key, defaultValue))
    }

    override fun getInt(key: String, defaultValue: Int): Preference<Int> {
        return PreferenceImpl(rxkPrefs.integer(key, defaultValue))
    }

    override fun getLong(key: String, defaultValue: Long): Preference<Long> {
        return PreferenceImpl(rxkPrefs.long(key, defaultValue))
    }

    override fun getString(key: String, defaultValue: String): Preference<String> {
        return PreferenceImpl(rxkPrefs.string(key, defaultValue))
    }

    override fun <M> getCustomModel(key: String, mapper: PreferenceMapper<M>): Preference<M> {
        return CustomModelPreferenceImpl(rxkPrefs.string(key), mapper)
    }

    override fun clear() = rxkPrefs.clear()

}