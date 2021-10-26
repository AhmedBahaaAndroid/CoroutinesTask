package com.example.freenowapp.errorHandling

import androidx.annotation.StringRes
import com.example.freenowapp.R

data class ErrorViewState(
    @StringRes var title: Int = R.string.un_expected_error_msg,
    var message: String = "",
    @StringRes var positiveActionTitle: Int = R.string.empty_state_view_retry_button_title,
    val positiveAction: (() -> Unit)? = null,
    @StringRes var negativeActionTitle: Int = R.string.empty_state_view_back_button_title,
    val negativeAction: (() -> Unit)? = null
)
