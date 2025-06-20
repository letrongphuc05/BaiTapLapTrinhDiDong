package com.example.thuchanh2day4

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thuchanh2day4.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(
            imageResId = R.drawable.logo2,
            title = "Easy Time Management",
            description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first."
        ),
        OnboardingPage(
            imageResId = R.drawable.logo3,
            title = "Increase Work Effectiveness",
            description = "Time management and the determination of more important tasks will give your job statistics better and always improve."
        ),
        OnboardingPage(
            imageResId = R.drawable.logo4,
            title = "Reminder Notification",
            description = "The advantage of this application is that it also provides reminders for you, so you don't forget to keep doing your assignments well and according to the time you have set."
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    // Hàm điều hướng đến màn hình chính
    fun navigateToMainApp() {
        navController.popBackStack()
        navController.navigate(ScreenRoute.MainApp.route)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Nút Skip vẫn sẽ vào trang chính
        TextButton(
            onClick = { navigateToMainApp() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Skip", color = Color.Gray, fontSize = 16.sp)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->
                OnboardingPageContent(page = pages[pageIndex])
            }

            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    IconButton(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }

                Button(
                    onClick = {
                        if (pagerState.currentPage < pages.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            // Khi ở trang cuối, nhấn nút sẽ quay về trang đầu tiên
                            scope.launch {
                                pagerState.animateScrollToPage(0)
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .width(150.dp)
                ) {
                    AnimatedContent(targetState = pagerState.currentPage == pages.size - 1, label = "ButtonText") { isLastPage ->
                        if (isLastPage) {
                            Text("Get Started", fontSize = 16.sp)
                        } else {
                            Text("Next", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 40.dp)
    ) {
        // Hiển thị hình ảnh từ thư mục drawable
        Image(
            painter = painterResource(id = page.imageResId),
            contentDescription = page.title,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1.1f),
            contentScale = ContentScale.Fit
        )

        if (page.title.isNotEmpty() || page.description.isNotEmpty()) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = page.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (page.description.isNotEmpty()) {
                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    color = Color.Gray,
                )
            }
        }
    }
}
