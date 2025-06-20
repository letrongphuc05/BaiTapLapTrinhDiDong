package com.example.quanlythuvien

import java.util.UUID

// Data class cho Sách
data class Book(
    val id: String = UUID.randomUUID().toString(),
    val title: String
)


data class Student(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val borrowedBookIds: MutableList<String> = mutableListOf()
)
