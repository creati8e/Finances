package serg.chuprin.finances.core.impl.data.datasource.preferences

import serg.chuprin.finances.core.api.data.datasource.preferences.mapper.PreferenceMapper

/**
 * Created by Sergey Chuprin on 03.06.2019.
 */
abstract class EnumPreferenceMapper<P> : PreferenceMapper<P> {

    protected abstract val preferenceValuesMap: Map<P, String>

    override fun toStringValue(model: P): String = requireNotNull(preferenceValuesMap[model]) {
        "No value for custom preference model found in map. Do you forgot to add it?"
    }

    override fun toModel(stringValue: String): P {
        return preferenceValuesMap
            .entries
            .find { (_, pref_key) -> pref_key == stringValue }
            ?.key
            ?: defaultModel
    }

}