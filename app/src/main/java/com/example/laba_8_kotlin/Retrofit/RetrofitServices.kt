package com.example.laba_8_kotlin.Retrofit

import com.example.laba_8_kotlin.data.ForecastResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("forecast")
    fun getForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<ForecastResponse>
}