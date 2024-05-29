package io.bash_psk.preference.datastore

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import io.bash_psk.preference.resource.ConstantError
import io.bash_psk.preference.utils.SetLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

fun <T> Context.getPreference(
    key: Preferences.Key<T>,
    initial: T
) : Flow<T> {

    val dataStore = this.dataStore

    return dataStore.data.catch { throwable: Throwable ->

        when (throwable) {

            is IOException -> {

                emit(value = emptyPreferences())

                SetLog.setError(
                    message = ConstantError.DATASTORE_ERROR,
                    throwable = throwable
                )
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

fun <T> Context.setPreference(
    key: Preferences.Key<T>,
    value: T
) {

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->

        SetLog.setError(
            message = ConstantError.APP_ERROR,
            throwable = throwable
        )
    }

    val dataStore = this.dataStore

    CoroutineScope(
        context = Dispatchers.IO
    ).launch(
        context = coroutineExceptionHandler
    ) {

        dataStore.edit { preferences: MutablePreferences ->

            preferences[key] = value
        }
    }
}