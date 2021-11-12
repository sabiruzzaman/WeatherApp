package com.babuland.weather.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.babuland.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getCityData()
        weatherAdapter = WeatherAdapter(this)


        initView()
        liveDataListener()
    }

    private fun initView() {

        binding.weatherRVId.layoutManager = LinearLayoutManager(this)
        binding.weatherRVId.adapter = weatherAdapter

        binding.swipeRefreshLayoutId.setOnRefreshListener {
            viewModel.getCityData()
        }
    }

    private fun liveDataListener() {

        viewModel.onProgress.observe(this, {
            binding.swipeRefreshLayoutId.isRefreshing = it
        })

        viewModel.onCityListSuccess.observe(this, {
            weatherAdapter.addData(it)
        })

        viewModel.onCityListFailed.observe(this, {
            binding.swipeRefreshLayoutId.isRefreshing = false

        })

    }


}