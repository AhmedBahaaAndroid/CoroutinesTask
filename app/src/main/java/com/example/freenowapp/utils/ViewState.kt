package com.example.freenowapp.utils

import com.example.freenowapp.errorHandling.AppException

sealed class ViewState {
    object Loading : ViewState()
    object Success : ViewState()
    data class Error(val error: AppException?) : ViewState()
}

