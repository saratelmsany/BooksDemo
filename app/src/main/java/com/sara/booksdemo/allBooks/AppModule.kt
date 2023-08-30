package com.sara.booksdemo.allBooks

import com.sara.booksdemo.App
import com.sara.booksdemo.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {

            val baseUrl =  App.getContext().resources.getString(R.string.base_url)
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(ApiInterface::class.java)


    }

}