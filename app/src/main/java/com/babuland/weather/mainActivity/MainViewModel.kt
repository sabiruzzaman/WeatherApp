package com.babuland.weather.mainActivity

import androidx.lifecycle.*
import com.babuland.weather.room.tables.WeatherTable
import com.babuland.weather.mainActivity.dataModel.WeatherModel
import com.babuland.weather.network.ServerDataRepo
import com.babuland.weather.room.RoomDataRepo
import com.babuland.weather.utils.CommonMethods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val serverDataRepo: ServerDataRepo,
    private val roomDataRepo: RoomDataRepo,
    private val commonMethods: CommonMethods
) : ViewModel() {

    val onProgress = MutableLiveData<Boolean>()
    val onCityListSuccess = MutableLiveData<List<WeatherModel.CityList>>()
    val onCityListFailed = MutableLiveData<String>()

    fun getCityData() {
        onProgress.postValue(true)

        viewModelScope.launch {
            if (commonMethods.isOnline()) {
                serverDataRepo.getCityList("23.68", "90.35", "50")
                    .catch {
                        onProgress.postValue(false)
                        onCityListFailed.postValue(it.message)
                    }.collect {
                        onProgress.postValue(false)
                        onCityListSuccess.postValue(it.getList() as List<WeatherModel.CityList>?)
                        storeInRoom(it.getList() as List<WeatherModel.CityList>)
                    }
            } else {
                getFromRoom()
            }
        }
    }


    private fun storeInRoom(list: List<WeatherModel.CityList>) {
        viewModelScope.launch {
            roomDataRepo.deleteAll()
            for (item in list) {
                val city = WeatherTable(
                    roomID = 0,
                    id = item.id!!,
                    name = item.name!!,
                    description = item.weather!![0].description.toString(),
                    lat = item.coord?.lat!!,
                    lon = item.coord?.lon!!,
                    temp = item.main?.temp!!,
                    temp_min = item.main?.tempMin!!,
                    temp_max = item.main?.tempMax!!,
                    humidity = item.main?.humidity!!,
                    speed = item.wind?.speed!!
                )
                roomDataRepo.insertWeather(city)
            }

        }

    }


    private fun getFromRoom() {
        val cityList = mutableListOf<WeatherModel.CityList>()

        viewModelScope.launch {
            roomDataRepo.getWeather()
                .collect {
                    for (item in it) {
                        val city = WeatherModel.CityList()
                        val main = WeatherModel.Main()
                        val coord = WeatherModel.Coord()
                        val wind = WeatherModel.Wind()
                        val weatherList = mutableListOf<WeatherModel.Weather>()
                        val weather = WeatherModel.Weather()

                        main.temp = item.temp
                        main.tempMin = item.temp_min
                        main.tempMax = item.temp_max
                        main.humidity = item.humidity

                        coord.lat = item.lat
                        coord.lon = item.lon

                        wind.speed = item.speed
                        city.id = item.id
                        city.name = item.name

                        weather.description = item.description
                        weatherList.add(weather)

                        city.weather = weatherList
                        city.coord = coord
                        city.main = main
                        city.wind = wind
                        cityList.add(city)
                    }

                    onProgress.postValue(false)
                    onCityListSuccess.postValue(cityList)
                }
        }

    }

}