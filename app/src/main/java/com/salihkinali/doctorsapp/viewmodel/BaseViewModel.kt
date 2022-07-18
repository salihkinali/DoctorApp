package com.salihkinali.doctorsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihkinali.doctorsapp.model.Doctor
import com.salihkinali.doctorsapp.model.DoctorsResponse
import com.salihkinali.doctorsapp.network.DoctorApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class BaseViewModel:ViewModel() {
    private val _doctorList = MutableLiveData<List<Doctor>>()
    val doctorList: LiveData<List<Doctor>> get() = _doctorList


    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            //_doctorList.value = DoctorApi.retrofitService.getDoctors()
            val response = DoctorApi.retrofitService.getDoctors()
            response.enqueue(object : retrofit2.Callback<DoctorsResponse>{
                override fun onResponse(
                    call: Call<DoctorsResponse>,
                    response: Response<DoctorsResponse>
                ) {
                    if(response.isSuccessful){
                        _doctorList.value = response.body()!!.doctors
                    }
                }

                override fun onFailure(call: Call<DoctorsResponse>, t: Throwable) {

                }

            })
        }
    }
}