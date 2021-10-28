package com.example.freenowapp.bases

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.freenowapp.R
import com.example.freenowapp.utils.ui.ErrorViewState
import com.example.freenowapp.utils.ui.setup

abstract class BaseFragment : Fragment() {
    private lateinit var contentView: View
    private lateinit var animationLoader: LottieAnimationView
    private lateinit var errorViewState: ErrorViewState
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.base_fragment, container, false)
        initRootView()
        return rootView
    }

    private fun initRootView() {
        contentView = rootView.findViewById(R.id.baseContentGv)
        animationLoader = rootView.findViewById(R.id.animationLoader)
        animationLoader.repeatCount = ValueAnimator.INFINITE
        animationLoader.setAnimation(LOADER_FILE_NAME)
        errorViewState = rootView.findViewById(R.id.errorViewState)
    }

    protected fun attachToRootView(content: View): View {
        (contentView as ViewGroup).addView(content)
        return rootView
    }

    protected open fun hideErrorState() {
        errorViewState.visibility = View.GONE
        errorViewState.cancelAnimation()
    }

    protected open fun showError(
        errorMessage: String, positiveAction: ((() -> Unit)),
        negativeAction: (() -> Unit)
    ) {
        errorViewState.setup(errorMessage,positiveAction,negativeAction)
        errorViewState.visibility = View.VISIBLE
    }


    protected open fun showLoading() {
        errorViewState.visibility = View.GONE
        animationLoader.visibility = View.VISIBLE
        animationLoader.playAnimation()
    }

    protected open fun hideLoading() {
        animationLoader.visibility = View.GONE
        animationLoader.cancelAnimation()
    }


    companion object {
        const val LOADER_FILE_NAME = "loader.json"
    }
}