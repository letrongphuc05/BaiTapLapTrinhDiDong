package com.example.navigation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI Components List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFADD8E6),
                    titleContentColor = Color(0xFF00008B)
                )
            )
        },
        containerColor = Color(0xFFF0F0F0)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Display
            item { SectionHeader(title = "Display", textColor = Color(0xFF00008B)) }
            item {
                MenuListItem(
                    title = "Text",
                    description = "Displays text",
                    titleColor = Color(0xFF00008B),
                    cardBackgroundColor = Color(0xFFB0E0E6)
                ) {
                    navController.navigate(Screen.TextDetail.route)
                }
            }
            item {
                MenuListItem(
                    title = "Image",
                    description = "Displays an image",
                    titleColor = Color(0xFF00008B),
                    cardBackgroundColor = Color(0xFFB0E0E6)
                ) {
                    navController.navigate(Screen.ImageDetail.route)
                }
            }

            // Input
            item { SectionHeader(title = "Input", textColor = Color(0xFF00008B)) }
            item {
                MenuListItem(
                    title = "TextField",
                    description = "Input field for text",
                    titleColor = Color(0xFF00008B),
                    cardBackgroundColor = Color(0xFFB0E0E6)
                ) {
                    navController.navigate(Screen.TextFieldDetail.route)
                }
            }
            item {
                MenuListItem(
                    title = "PasswordField",
                    description = "Input field for passwords",
                    titleColor = Color(0xFF00008B),
                    cardBackgroundColor = Color(0xFFB0E0E6)
                ) {
                    navController.navigate(Screen.PasswordFieldDetail.route)
                }
            }

            // Layout
            item { SectionHeader(title = "Layout", textColor = Color(0xFF00008B)) }
            item {
                MenuListItem(
                    title = "Column",
                    description = "Arranges elements vertically",
                    titleColor = Color(0xFF00008B),
                    cardBackgroundColor = Color(0xFFB0E0E6)
                ) {
                    navController.navigate(Screen.ColumnDetail.route)
                }
            }
            item {
                MenuListItem(
                    title = "Row",
                    description = "Arranges elements horizontally",
                    titleColor = Color(0xFF00008B),
                    cardBackgroundColor = Color(0xFFB0E0E6)
                ) {
                    navController.navigate(Screen.RowDetail.route)
                }
            }
            item {
                MenuListItem(
                    title = "Box",
                    description = "Stacks or aligns elements",
                    titleColor = Color(0xFF00008B),
                    cardBackgroundColor = Color(0xFFB0E0E6)
                ) {
                    navController.navigate(Screen.BoxDetail.route)
                }
            }

        }
    }
}


@Composable
fun MenuListItem(
    title: String,
    description: String,
    titleColor: Color,
    cardBackgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor,
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = titleColor)
            Text(text = description, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SectionHeader(title: String, textColor: Color) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp),
        color = textColor
    )
}