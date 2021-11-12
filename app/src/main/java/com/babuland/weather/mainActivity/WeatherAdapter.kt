package com.babuland.weather.mainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.babuland.weather.map.MapActivity
import com.babuland.weather.R
import com.babuland.weather.databinding.ItemWeatherBinding
import com.babuland.weather.mainActivity.dataModel.WeatherModel
import com.babuland.weather.utils.Singleton

class WeatherAdapter(val context: Context) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemWeatherBinding.bind(itemView)
    }

    private val cityList = mutableListOf<WeatherModel.CityList>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<WeatherModel.CityList>) {
        cityList.clear()
        cityList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val city = cityList[position]
        with(holder) {
            binding.cityNameTvId.text = city.name
            binding.cloudTypeTvId.text = city.weather!![0].description
            binding.temperatureTvId.text = (city.main?.temp!! - 273.15F).toInt().toString() +
                    context.resources.getString(R.string.degree_symbol)
        }

        holder.itemView.setOnClickListener {
            Singleton.INSTANCE.cityDetails = city
            context.startActivity(Intent(context, MapActivity::class.java))
        }

    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}