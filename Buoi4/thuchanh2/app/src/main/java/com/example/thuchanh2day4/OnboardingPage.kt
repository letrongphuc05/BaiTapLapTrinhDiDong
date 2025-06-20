package com.example.thuchanh2day4

import androidx.annotation.DrawableRes

data class OnboardingPage(
    @DrawableRes val imageResId: Int,
    val title: String,
    val description: String
)
