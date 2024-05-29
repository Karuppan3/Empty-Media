package io.bash_psk.preference.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import io.bash_psk.preference.resource.ConstantDatastore

val Context.dataStore by preferencesDataStore(
    name = ConstantDatastore.DATASTORE_NAME
)