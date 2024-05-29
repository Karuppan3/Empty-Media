package io.bash_psk.empty_media.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bash_psk.empty_media.data.database.MediaDatabase
import io.bash_psk.empty_media.data.repository.EmptyDatabaseImpl
import io.bash_psk.empty_media.data.repository.EmptyDatastoreImpl
import io.bash_psk.empty_media.data.repository.EmptyMediaImpl
import io.bash_psk.empty_media.data.repository.EmptyNotificationImpl
import io.bash_psk.empty_media.data.repository.EmptyPermissionImpl
import io.bash_psk.empty_media.data.repository.EmptyStorageImpl
import io.bash_psk.empty_media.data.repository.EmptyWorkerImpl
import io.bash_psk.empty_media.domain.database.MediaDao
import io.bash_psk.empty_media.domain.repository.EmptyDatabase
import io.bash_psk.empty_media.domain.repository.EmptyDatastore
import io.bash_psk.empty_media.domain.repository.EmptyMedia
import io.bash_psk.empty_media.domain.repository.EmptyNotification
import io.bash_psk.empty_media.domain.repository.EmptyPermission
import io.bash_psk.empty_media.domain.repository.EmptyStorage
import io.bash_psk.empty_media.domain.repository.EmptyWorker
import io.bash_psk.empty_media.domain.resource.ConstantDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideEmptyPermission(
        application: Application
    ) : EmptyPermission {

        return EmptyPermissionImpl(application = application)
    }

    @Provides
    @Singleton
    fun provideEmptyStorage(
        application: Application
    ) : EmptyStorage {

        return EmptyStorageImpl(application = application)
    }

    @Provides
    @Singleton
    fun provideEmptyDatastore(
        application: Application
    ) : EmptyDatastore {

        return EmptyDatastoreImpl(application = application)
    }

    @Provides
    @Singleton
    fun provideMediaDatabase(
        application: Application
    ) : MediaDatabase {

        return Room.databaseBuilder(
            application,
            MediaDatabase::class.java,
            ConstantDatabase.MAIN_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMediaDao(
        mediaDatabase: MediaDatabase
    ) : MediaDao {

        return mediaDatabase.mediaDao
    }

    @Provides
    @Singleton
    fun provideEmptyDatabase(
        mediaDao: MediaDao
    ) : EmptyDatabase {

        return EmptyDatabaseImpl(mediaDao = mediaDao)
    }

    @Provides
    @Singleton
    fun provideEmptyNotification(
        application: Application
    ) : EmptyNotification {

        return EmptyNotificationImpl(application = application)
    }

    @Provides
    @Singleton
    fun provideEmptyWorker(
        application: Application
    ) : EmptyWorker {

        return EmptyWorkerImpl(application = application)
    }

    @Provides
    @Singleton
    fun provideEmptyMedia(
        application: Application
    ) : EmptyMedia {

        return EmptyMediaImpl(application = application)
    }
}