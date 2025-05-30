package com.example.laba_8_kotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laba_8_kotlin.Retrofit.Common
import com.example.laba_8_kotlin.Retrofit.RetrofitServices
import com.example.laba_8_kotlin.data.ForecastResponse
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
const val API_KEY = "20858ea2833fa2cd74c3978b79c8f71e"

class MainActivity : AppCompatActivity() {
    private lateinit var mService: RetrofitServices
    private var adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rView)
        mService = Common.retrofitService
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        getAllWeatherList()
    }

    private fun getAllWeatherList() {
        mService.getForecast("Shklov", API_KEY, "metric").enqueue(object : Callback<ForecastResponse> {
            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e("WEATHER_API", "Error: ${t.message}")


            }

            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { body ->
                        val list = body.list
                        adapter.submitList(list)
                    }

                    Log.d("WEATHER_API", "Response: ${response.body()?.list}")
                }
            }
        })
    }
}