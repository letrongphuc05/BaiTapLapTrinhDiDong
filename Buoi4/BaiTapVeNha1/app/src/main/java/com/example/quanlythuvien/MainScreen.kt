package com.example.quanlythuvien

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quanlythuvien.ui.theme.CheckboxRed

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    val currentStudent by viewModel.currentStudent
    val borrowedBooks = viewModel.getBorrowedBooksForCurrentStudent()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1. Tiêu đề chính
        Text(
            text = "Hệ thống\nQuản lý Thư viện",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )

        // 2. Khu vực chọn sinh viên
        Text("Sinh viên", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = currentStudent?.name ?: "Chưa chọn sinh viên",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.4f)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { navController.navigate(BottomNavItem.Students.route) },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text("Thay đổi")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Khu vực danh sách sách đã mượn
        Text("Danh sách sách", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            if (borrowedBooks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Bạn chưa mượn quyển sách nào.\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(borrowedBooks, key = { it.id }) { book ->
                        BorrowedBookItem(
                            book = book,
                            onReturnClick = {
                                currentStudent?.let { student ->
                                    viewModel.returnBook(student.id, book.id)
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Nút "Thêm"
        Button(
            onClick = { navController.navigate(BottomNavItem.AllBooks.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Thêm", fontSize = 18.sp)
        }
    }
}

@Composable
fun BorrowedBookItem(book: Book, onReturnClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = true,
                onCheckedChange = { isChecked ->
                    if (!isChecked) {
                        onReturnClick()
                    }
                },
                // Sử dụng màu đỏ cho checkbox
                colors = CheckboxDefaults.colors(
                    checkedColor = CheckboxRed,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = book.title,
                modifier = Modifier.padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
