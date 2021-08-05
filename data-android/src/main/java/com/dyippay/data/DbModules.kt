package com.dyippay.data

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.dyippay.data.daos.AudioBookDao
import com.dyippay.data.daos.ItemDao
import com.dyippay.data.daos.MovieDao
import com.dyippay.data.daos.SongDao
import com.dyippay.data.daos.TokenDao
import com.dyippay.data.daos.TvShowDao
import com.dyippay.data.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModules {

    @Suppress("SpreadOperator")
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DyippayRoomDatabase {
        val dbName = "dyippay.db"
        val builder = Room.databaseBuilder(
            context, DyippayRoomDatabase::class.java,
            dbName
        ).addMigrations(*DyippayRoomDatabase_Migrations.build()) // autogenerated code by Roomigrant
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }

        if (!BuildConfig.DEBUG) {
            val passphrase = SQLiteDatabase.getBytes(dbName.toCharArray())
            val factory = SupportFactory(passphrase)
            builder.openHelperFactory(factory)
        }
        return builder.build()
    }

    @Provides
    fun provideItemDao(db: DyippayRoomDatabase): ItemDao = db.itemDao()

    @Provides
    fun provideTvShowDao(db: DyippayRoomDatabase): TvShowDao = db.tvShowDao()

    @Provides
    fun provideSongDao(db: DyippayRoomDatabase): SongDao = db.songDao()

    @Provides
    fun provideMovieDao(db: DyippayRoomDatabase): MovieDao = db.movieDao()

    @Provides
    fun provideAudioBookDao(db: DyippayRoomDatabase): AudioBookDao = db.audioBookDao()

    @Provides
    fun provideTokenDao(db: DyippayRoomDatabase): TokenDao = db.tokenDao()

    @Provides
    fun provideUserDao(db: DyippayRoomDatabase): UserDao = db.userDao()
}
