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

inline fun <K, V> buildSortedMap(
    comparator: Comparator<K>,
    builderAction: MutableMap<K, V>.() -> Unit
): Map<K, V> {
    return sortedMapOf<K, V>(comparator).apply(builderAction)
}

fun <T> Collection<T>.contains(selector: (T) -> Boolean): Boolean = find(selector) != null

inline fun <reified R> Collection<Any>.containsType(): Boolean = find { it is R } != null