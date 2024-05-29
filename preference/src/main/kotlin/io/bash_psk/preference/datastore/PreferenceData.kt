package io.bash_psk.preference.datastore

import androidx.datastore.preferences.core.Preferences
import io.bash_psk.preference.map.PreferenceMap
import io.bash_psk.preference.resource.ConstantDatastore
import io.bash_psk.preference.settings.AppTheme

@androidx.annotation.OptIn(
    androidx.media3.common.util.UnstableApi::class
)
sealed class PreferenceData<PK, EK, EV>(
    val title: String,
    val summary: String,
    val key: Preferences.Key<PK>,
    val initial: PK,
    val entities: Map<EK, EV>
) {

    data object ProfileNamePreference : PreferenceData<String, String, String>(
        title = ConstantDatastore.PROFILE_NAME_TITLE,
        summary = ConstantDatastore.NONE,
        key = ConstantDatastore.PROFILE_NAME_KEY,
        initial = ConstantDatastore.PROFILE_NAME_INITIAL,
        entities = emptyMap()
    )

    data object AppThemePreference : PreferenceData<String, String, String>(
        title = ConstantDatastore.APPLICATION_THEME_TITLE,
        summary = ConstantDatastore.NONE,
        key = ConstantDatastore.APPLICATION_THEME_KEY,
        initial = AppTheme.SystemTheme.theme,
        entities = PreferenceMap.APP_THEME_MAP
    )

    data object YtdlLibrary : PreferenceData<String, String, String>(
        title = ConstantDatastore.YTDL_LIBRARY_TITLE,
        summary = ConstantDatastore.NONE,
        key = ConstantDatastore.YTDL_LIBRARY_KEY,
        initial = ConstantDatastore.YTDL_LIBRARY_INITIAL,
        entities = emptyMap()
    )
}