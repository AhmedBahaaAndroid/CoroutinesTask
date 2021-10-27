package com.example.freenowapp.utils

import androidx.annotation.StringRes

sealed class ViewState {
    object Loading : ViewState()
    object Success : ViewState()

    data class Error(@StringRes val  error: Int) : ViewState()
}

