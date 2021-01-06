package serg.chuprin.finances.core.impl.data.datasource.preferences

import com.afollestad.rxkprefs.Pref
import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.data.datasource.preferences.Preference

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */
internal class PreferenceImpl<T : Any>(
    private val pref: Pref<T>
) : Preference<T> {

    override var value: T
        get() = pref.get()
        set(value) = pref.set(value)

    override val key: String
        get() = pref.key()

    override val defaultValue: T
        get() = pref.defaultValue()

    override val isSet
        get() = pref.isSet()

    override val asFlow: Flow<T>
        get() = pref.asFlow()

    override fun delete() = pref.delete()

    override fun setter(value: T) {
        this.value = value
    }

}