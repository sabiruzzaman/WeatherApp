package com.babuland.weather.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.babuland.weather.room.tables.WeatherTable

@Database(entities = [WeatherTable::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun dao(): DAO

}