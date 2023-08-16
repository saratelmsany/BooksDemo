package com.sara.booksdemo.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [Book::class],
    version = 2,
  //  autoMigrations = [AutoMigration (from = 1, to = 2)]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    @TypeConverters(Converters::class)
    abstract fun bookDao(): BookDAO

}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE book "+
                "ADD COLUMN image TEXT"
        )
    }
}
    private lateinit var INSTANCE: AppDatabase

    fun getDatabase(context: Context): AppDatabase {
        synchronized(AppDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "books-database")
        //            .addMigrations(MIGRATION_1_2)
                    .build()

            }
        }
        return INSTANCE
    }
