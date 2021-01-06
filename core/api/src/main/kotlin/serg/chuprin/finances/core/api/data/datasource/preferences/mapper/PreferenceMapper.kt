package serg.chuprin.finances.core.api.data.datasource.preferences.mapper

/**
 * Created by Sergey Chuprin on 03.06.2019.
 */
interface PreferenceMapper<P> {

    val defaultValue: String
        get() = toStringValue(defaultModel)

    val defaultModel: P

    fun toStringValue(model: P): String

    fun toModel(stringValue: String): P

}