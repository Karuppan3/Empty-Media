package io.bash_psk.empty_media.data.repository

import android.app.Application
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import io.bash_psk.empty_media.domain.repository.EmptyDatastore
import io.bash_psk.empty_media.domain.resource.ConstantError
import io.bash_psk.preference.datastore.dataStore
import io.bash_psk.utils.log.SetLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class EmptyDatastoreImpl(
    application: Application
) : EmptyDatastore {

    private val dataStore = application.dataStore

    override suspend fun <T> getPreference(
        key: Preferences.Key<T>,
        initial: T
    ) : Flow<T> {

        return dataStore.data.catch { throwable: Throwable ->

            when (throwable) {

                is IOException -> {

                    emit(value = emptyPreferences())
                }

                else -> {

                    SetLog.setError(
                        message = ConstantError.DATASTORE_ERROR,
                        throwable = throwable
                    )
                }
            }
        }.map { preferences: Preferences ->

            preferences[key] ?: initial
        }
    }

    override suspend fun <T> setPreference(
        key: Preferences.Key<T>,
        value: T
    ) {

        dataStore.edit { preferences: MutablePreferences ->

            preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(
        key: Preferences.Key<T>
    ) {

        dataStore.edit { preferences: MutablePreferences ->

            preferences.remove(key = key)
        }
    }

    override suspend fun clearPreference() {

        dataStore.edit { preferences: MutablePreferences ->

            preferences.clear()
        }
    }
}