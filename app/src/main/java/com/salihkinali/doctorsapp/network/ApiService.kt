package com.salihkinali.doctorsapp.network

import com.google.gson.Gson
import com.salihkinali.doctorsapp.model.DoctorsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.mobillium.com/"

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
@GET("android/doctors.json")
fun getDoctors():Call<DoctorsResponse>
}

object DoctorApi{
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}