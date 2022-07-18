package com.salihkinali.doctorsapp.model


import com.google.gson.annotations.SerializedName

data class DoctorsResponse(
    @SerializedName("doctors")
    val doctors: List<Doctor>
)