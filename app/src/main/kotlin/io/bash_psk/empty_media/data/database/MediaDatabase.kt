package io.bash_psk.empty_media.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.bash_psk.empty_media.domain.database.MediaDao
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.resource.ConstantDatabase

@Database(
    entities = [MediaEntity::class],
    version = ConstantDatabase.MAIN_DATABASE_VERSION,
    exportSchema = false
)
abstract class MediaDatabase : RoomDatabase() {

    abstract val mediaDao : MediaDao
}