package com.babuland.weather.di

import android.content.Context
import androidx.room.Room
import com.babuland.weather.room.DAO
import com.babuland.weather.room.RoomDB
import com.babuland.weather.network.ApiService
import com.babuland.weather.room.RoomDataRepo
import com.babuland.weather.utils.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(context, RoomDB::class.java, "myDatabase").build()

    @Provides
    fun provideDao(roomDB: RoomDB): DAO =
        roomDB.dao()

    @Provides
    fun provideRoomRepo(dao: DAO): RoomDataRepo =
        RoomDataRepo(dao)

}