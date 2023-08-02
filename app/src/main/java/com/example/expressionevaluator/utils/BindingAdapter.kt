package com.example.expressionevaluator.utils

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun setProgressBarVisibility(progressBar: ProgressBar, isVisible: Boolean) {
    progressBar.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}