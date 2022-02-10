package fr.haumey.leboncointest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.haumey.leboncointest.model.Title

@Database(entities = [Title::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun titleDao(): TitleDao

    companion object {
        private const val DATABASE_NAME = "leboncointest_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}