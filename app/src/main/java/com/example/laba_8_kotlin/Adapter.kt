package com.example.laba_8_kotlin

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.laba_8_kotlin.data.WeatherItem

class Adapter(
) : ListAdapter<WeatherItem, RecyclerView.ViewHolder>(WeatherDiffCallback()) {

    inner class ViewHolderHot(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dt: TextView = itemView.findViewById(R.id.date)
        val icon: ImageView = itemView.findViewById(R.id.temperature_icon)
        val temp: TextView = itemView.findViewById(R.id.temperature)
        val backGroundColor: ConstraintLayout = itemView.findViewById(R.id.main_cont)
    }

    inner class ViewHolderCold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dt: TextView = itemView.findViewById(R.id.date)
        val icon: ImageView = itemView.findViewById(R.id.temperature_icon)
        val temp: TextView = itemView.findViewById(R.id.temperature)
        val backGroundColor: ConstraintLayout = itemView.findViewById(R.id.main_cont)
    }

    override fun getItemViewType(position: Int): Int {
        val weatherItem = getItem(position)
        return if (weatherItem.main.temp > 0) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ViewHolderHot(
                LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false)
            )
            0 -> ViewHolderCold (
                LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val weatherItem = getItem(position)
        val iconUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"
        when (holder) {
            is ViewHolderHot -> {
                holder.dt.text = weatherItem.dt_txt
                holder.dt.setPadding(0,0,0,0)
                holder.temp.text = weatherItem.main.temp.toString()
                holder.backGroundColor.setBackgroundResource(R.color.red)
                Glide.with(holder.itemView.context)
                    .load(iconUrl)
                    .into(holder.icon)
            }
            is ViewHolderCold -> {
                holder.dt.text = weatherItem.dt_txt
                holder.dt.setPadding(0,15,0,15)
                holder.temp.text = weatherItem.main.temp.toString()
                holder.backGroundColor.setBackgroundResource(R.color.blue)
                Glide.with(holder.itemView.context)
                    .load(iconUrl)
                    .into(holder.icon)
            }
        }
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