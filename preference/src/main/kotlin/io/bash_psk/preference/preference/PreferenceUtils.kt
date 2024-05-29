package io.bash_psk.preference.preference

object PreferenceUtils {

    fun <K, V> getReversedMap(
        itemMap: Map<K, V>
    ) : Map<V, K> {

        return itemMap.entries.associate { (key, value) ->

            value to key
        }
    }
}