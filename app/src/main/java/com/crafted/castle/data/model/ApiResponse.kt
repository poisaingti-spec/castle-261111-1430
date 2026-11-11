package com.crafted.castle.data.model

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String? = null,
    val errorCode: Int? = null
)

data class ContentListResponse(
    val items: List<Content>,
    val total: Int,
    val page: Int,
    val hasMore: Boolean
)

data class CategoryListResponse(
    val categories: List<Category>
)

data class ConfigResponse(
    val apiBaseUrl: String,
    val cdnUrl: String,
    val imageBaseUrl: String,
    val updateRequired: Boolean,
    val minVersion: String,
    val maintenanceMode: Boolean,
    val features: FeatureFlags
)

data class FeatureFlags(
    val enableDownloads: Boolean = true,
    val enableLiveTV: Boolean = true,
    val enableSports: Boolean = true,
    val enableShorts: Boolean = true,
    val enableCasting: Boolean = true,
    val enableOfflineMode: Boolean = true
)