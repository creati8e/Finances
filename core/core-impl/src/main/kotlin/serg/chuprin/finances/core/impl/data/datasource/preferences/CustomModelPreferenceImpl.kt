package serg.chuprin.finances.core.impl.data.datasource.preferences

import com.afollestad.rxkprefs.Pref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.data.datasource.preferences.Preference
import serg.chuprin.finances.core.api.data.datasource.preferences.mapper.PreferenceMapper

/**
 * Created by Sergey Chuprin on 03.06.2019.
 */
internal class CustomModelPreferenceImpl<M>(
    private val pref: Pref<String>,
    private val mapper: PreferenceMapper<M>
) : Preference<M> {

    override var value: M
        get() = mapper.toModel(pref.get())
        set(value) = pref.set(mapper.toStringValue(value))

    override val key: String
        get() = pref.key()

    override val defaultValue: M
        get() = mapper.defaultModel

    override val isSet: Boolean
        get() = pref.isSet()

    override val asFlow: Flow<M>
        get() = pref.asFlow().map { s -> mapper.toModel(s) }

    override fun delete() = pref.delete()

    override fun setter(value: M) {
        this.value = value
    }

}