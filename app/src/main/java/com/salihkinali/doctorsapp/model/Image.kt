package com.salihkinali.doctorsapp.model


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("url")
    val url: String
)