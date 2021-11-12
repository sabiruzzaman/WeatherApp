package com.babuland.weather.network

import com.babuland.weather.utils.Config
import javax.inject.Inject

class ApiServiceImplement @Inject constructor(private val apiService: ApiService) {

    suspend fun getCityList(lat: String, lon: String, cnt: String) =
        apiService.getCityList(lat, lon, cnt, Config.apiKey)
}