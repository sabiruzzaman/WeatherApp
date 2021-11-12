package com.babuland.weather.network

import com.babuland.weather.mainActivity.dataModel.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ServerDataRepo @Inject constructor(private val apiServiceImplement: ApiServiceImplement) {

    fun getCityList(lat: String, lon: String, cnt: String): Flow<WeatherModel> = flow {
        val response = apiServiceImplement.getCityList(lat, lon, cnt)
        emit(response)
    }.flowOn(Dispatchers.IO)
}