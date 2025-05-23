package com.example.laba_8_kotlin.data

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val volume: Double
)