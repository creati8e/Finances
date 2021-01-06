package serg.chuprin.finances.core.impl.data.datasource.preferences

import serg.chuprin.finances.core.api.data.datasource.preferences.Preference
import kotlin.reflect.KProperty

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */

fun <T> preference(pref: Preference<T>) = PreferenceDelegate(pref)

class PreferenceDelegate<T>(private val pref: Preference<T>) {

    operator fun getValue(thisRef: Any, property: KProperty<*>): T = pref.value

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        pref.value = value
    }

}