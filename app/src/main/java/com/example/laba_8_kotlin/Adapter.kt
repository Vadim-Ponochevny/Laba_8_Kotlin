package com.example.laba_8_kotlin

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.laba_8_kotlin.data.WeatherItem

class Adapter(
) : ListAdapter<WeatherItem, Adapter.ViewHolder>(WeatherDiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dt: TextView = itemView.findViewById(R.id.date)
        val icon: ImageView = itemView.findViewById(R.id.temperature_icon)
        val temp: TextView = itemView.findViewById(R.id.temperature)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("AdapterDebug", "onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem = getItem(position)
        Log.d("AdapterDebug", "onBindViewHolder called for position $position: $weatherItem")
        holder.dt.text = weatherItem.dt_txt
        holder.temp.text = weatherItem.main.temp.toString()

        val iconCode = weatherItem.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"


        Glide.with(holder.itemView.context)
            .load(iconUrl)
            .into(holder.icon)
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherItem>() {
    override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
        return oldItem.dt_txt == newItem.dt_txt
    }
}