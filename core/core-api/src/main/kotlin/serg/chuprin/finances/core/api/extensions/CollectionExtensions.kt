package serg.chuprin.finances.core.api.extensions

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
fun <K, V> nonNullValuesMap(vararg pairs: Pair<K, V?>): Map<K, V> {
    return if (pairs.isEmpty()) {
        emptyMap()
    } else {
        LinkedHashMap<K, V>().apply {
            pairs.forEach { (k, v) ->
                if (v != null) {
                    put(k, v)
                }
            }
        }
    }
}

fun <T> Collection<T>.contains(selector: (T) -> Boolean): Boolean = find(selector) != null