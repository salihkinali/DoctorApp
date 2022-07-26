package com.salihkinali.doctorsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihkinali.doctorsapp.model.Doctor
import com.salihkinali.doctorsapp.model.DoctorsResponse
import com.salihkinali.doctorsapp.network.DoctorApi
import com.salihkinali.doctorsapp.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class BaseViewModel : ViewModel() {
    private val _doctorList = MutableLiveData<List<Doctor>>()
    val doctorList: LiveData<List<Doctor>> get() = _doctorList

    private var currentList: List<Doctor>? = null

    private var _status = MutableLiveData<NetworkResult>()
    val status: LiveData<NetworkResult> get() = _status

    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = DoctorApi.retrofitService.getDoctors()
                response.enqueue(object : retrofit2.Callback<DoctorsResponse> {
                    override fun onResponse(
                        call: Call<DoctorsResponse>,
                        response: Response<DoctorsResponse>
                    ) {
                        if (response.isSuccessful) {
                            _status.value = NetworkResult.Success("great")
                            _doctorList.value = response.body()!!.doctors
                            currentList = response.body()!!.doctors
                        }
                    }

                    override fun onFailure(call: Call<DoctorsResponse>, t: Throwable) {
                        _status.value = NetworkResult.Failure(t)
                    }

                })
            } catch (e: Exception) {
                _status.value = NetworkResult.Error(e)
            }
        }
    }

    fun searchList(query: String?, gender: String?) {
        if (query.isNullOrEmpty() && gender.isNullOrEmpty()) {
            _doctorList.value = currentList!!
        } else if (query.isNullOrEmpty()) {
            // Only making filter for gender
            _doctorList.value = currentList?.filter {
                it.gender == gender
            }
        } else if (gender.isNullOrEmpty()) {
            // Only making filter for query
            _doctorList.value = currentList!!.filter {
                it.fullName.lowercase().contains(query.lowercase(), ignoreCase = true)
            }
        } else {
            _doctorList.value = currentList!!.filter {
                it.fullName.lowercase().contains(query.trim(), ignoreCase = true) &&
                        it.gender == gender
            }
        }
    }

}