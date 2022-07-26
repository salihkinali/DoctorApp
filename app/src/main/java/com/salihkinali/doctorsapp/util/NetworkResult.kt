package com.salihkinali.doctorsapp.util

import com.salihkinali.doctorsapp.model.Doctor

sealed class NetworkResult {
    data class Success(val success: String) : NetworkResult()
    data class Error(val exception: Exception) : NetworkResult()
    data class Failure(val throwable: Throwable) : NetworkResult()
}
