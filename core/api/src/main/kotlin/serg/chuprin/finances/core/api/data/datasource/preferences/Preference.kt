package serg.chuprin.finances.core.api.data.datasource.preferences

import kotlinx.coroutines.flow.Flow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */
interface Preference<T> {

    /**
     * Returns current or default value.
     */
    var value: T

    val key: String

    val defaultValue: T

    /**
     * Deletes any existing value for the preference.
     */
    fun delete()

    val isSet: Boolean

    fun setter(value: T)

    val asFlow: Flow<T>

    fun valueDelegate(): ReadWriteProperty<Any, T> {
        return object : ReadWriteProperty<Any, T> {

            override fun getValue(thisRef: Any, property: KProperty<*>): T = value

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                this@Preference.value = value
            }
        }
    }

}