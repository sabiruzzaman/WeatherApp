package com.babuland.weather.utils

import com.babuland.weather.mainActivity.dataModel.WeatherModel

class Singleton {

    companion object {
        val INSTANCE = Singleton()
    }

    lateinit var cityDetails: WeatherModel.CityList

}