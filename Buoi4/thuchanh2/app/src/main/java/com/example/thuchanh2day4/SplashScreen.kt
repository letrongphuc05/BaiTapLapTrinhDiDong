package com.example.thuchanh2day4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.thuchanh2day4.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.popBackStack()
        navController.navigate(ScreenRoute.Onboarding.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), 
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "UTH Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth(1.0f)
        )
    }
}
