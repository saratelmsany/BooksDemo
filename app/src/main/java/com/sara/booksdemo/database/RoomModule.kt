package com.sara.booksdemo.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideBooksDatabase(@ApplicationContext context :Context): AppDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,"books-database").build()
    }

    @Provides
    fun provideBooksDao(booksDatabase: AppDatabase): BookDAO{
        return booksDatabase.bookDao()
    }
}