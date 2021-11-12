package com.babuland.weather.map

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.babuland.weather.R
import com.babuland.weather.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.babuland.weather.mainActivity.dataModel.WeatherModel
import com.babuland.weather.utils.Singleton

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private lateinit var cityDetails: WeatherModel.CityList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cityDetails = Singleton.INSTANCE.cityDetails

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragmentId) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initView()
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.backImgId.setOnClickListener {
            onBackPressed()
        }

        binding.cityNameTvId.text = cityDetails.name
        binding.temperatureTvId.text =
            ((cityDetails.main?.temp!! - 273.15F).toInt().toString()) + "°c"
        binding.maxTempTvId.text =
            "Max temp: " + ((cityDetails.main?.tempMax!! - 273.15F).toInt().toString()) + "°c"
        binding.minTempTvId.text =
            "Min temp: " + ((cityDetails.main?.tempMin!! - 273.15F).toInt().toString()) + "°c"
        binding.humidityTvId.text = "Humidity: " + cityDetails.main?.humidity.toString()
        binding.descriptionTvId.text = cityDetails.weather!![0].description
        binding.speedTvId.text = "Wind speed: " + cityDetails.wind?.speed.toString()
    }

    override fun onMapReady(googleMaps: GoogleMap) {
        googleMap = googleMaps
        val sydney = LatLng(cityDetails.coord?.lat!!, cityDetails.coord!!.lon!!)
        googleMap.addMarker(MarkerOptions().position(sydney).title(cityDetails.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))
    }
}