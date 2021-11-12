package com.babuland.weather.room

import androidx.room.*
import com.babuland.weather.room.tables.WeatherTable

@Dao
interface DAO {

    @Insert
    suspend fun insertWeather(weatherTable: WeatherTable): Long

    @Query("DELETE FROM city")
    suspend fun deleteAll()

    @Query("SELECT * FROM city")
    fun getAllWeather(): List<WeatherTable>

}