package com.example.sepic.API

data class ApiResponse(
    val error: Boolean,
    val message: String,
    val count: Int,
    val data: List<AppData>
)