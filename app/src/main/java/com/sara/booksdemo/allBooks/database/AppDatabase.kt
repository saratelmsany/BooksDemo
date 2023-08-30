package com.sara.booksdemo.allBooks.database

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sara.booksdemo.allBooks.Converters


@Database(
    entities = [Book::class],
    version = 4,
    autoMigrations = [AutoMigration (from = 3, to = 4)]
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

//    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL(
//                "ALTER TABLE book " +
//                        "ADD COLUMN authors TEXT"
//            )
//        }
//    }
}
//    private lateinit var INSTANCE: AppDatabase
//
//    fun getDatabase(context: Context): AppDatabase {
//        synchronized(AppDatabase::class.java) {
//            if (!::INSTANCE.isInitialized) {
//                INSTANCE = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "books-database")
//        //            .addMigrations(MIGRATION_1_2)
//                    .build()
//
//            }
//        }
//        return INSTANCE
//    }
