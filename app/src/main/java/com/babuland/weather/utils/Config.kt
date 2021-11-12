package com.babuland.weather.utils

import com.babuland.weather.BuildConfig

class Config {
    companion object {
        const val baseUrl = BuildConfig.BASE_URL
        const val apiKey = BuildConfig.API_KEY
    }
}