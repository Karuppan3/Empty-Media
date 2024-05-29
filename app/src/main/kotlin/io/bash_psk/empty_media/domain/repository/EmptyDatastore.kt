package io.bash_psk.empty_media.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface EmptyDatastore {

    suspend fun <T> getPreference(
        key: Preferences.Key<T>,
        initial: T
    ) : Flow<T>

    suspend fun <T> setPreference(
        key: Preferences.Key<T>,
        value: T
    )

    suspend fun <T> removePreference(
        key: Preferences.Key<T>
    )

    suspend fun clearPreference()
}