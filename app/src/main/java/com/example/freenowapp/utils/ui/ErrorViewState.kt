package com.example.freenowapp.utils.ui

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.airbnb.lottie.LottieAnimationView
import com.example.freenowapp.R
import com.example.freenowapp.databinding.ErrorViewBinding
import com.example.freenowapp.ui.homeView.MainActivity

class ErrorViewState @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var errorAnimationLoader: LottieAnimationView
    private var binding: ErrorViewBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.error_view, this, true)

    init {
        initView()
    }

    private fun initView() {
        errorAnimationLoader = binding.errorAnimatedImage
        errorAnimationLoader.repeatCount = ValueAnimator.INFINITE
        errorAnimationLoader.setAnimation(ERROR_LOADER_FILE_NAME)
    }

    fun setErrorMessage(description: String) {
        binding.blockingStateDescriptionLabel.text = description
    }


    fun setPositiveAction(actionClickListener: (() -> Unit)?) {
        binding.retryButton.setOnClickListener {
            actionClickListener?.invoke()
        }
    }

    fun cancelAnimation() {
        errorAnimationLoader.visibility = View.GONE
        errorAnimationLoader.cancelAnimation()
    }

    fun setNegativeAction(actionClickListener: (() -> Unit)) {
        binding.backButton.setOnClickListener {
            actionClickListener.invoke()
        }
    }

    companion object {
        const val ERROR_LOADER_FILE_NAME = "errorimage.json"
    }
}

fun ErrorViewState.setup(
    message: String, positiveAction: ((() -> Unit)),
    negativeAction: (() -> Unit)
) {
    this.setErrorMessage(message)
    this.setPositiveAction(positiveAction)
    this.setNegativeAction(negativeAction)
}
