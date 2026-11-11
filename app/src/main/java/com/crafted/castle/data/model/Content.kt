package com.crafted.castle.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(
    val id: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val backdropUrl: String,
    val videoUrl: String,
    val trailerUrl: String? = null,
    val duration: Int, // in minutes
    val releaseYear: Int,
    val rating: Float,
    val voteCount: Int,
    val contentType: ContentType,
    val genres: List<String>,
    val cast: List<CastMember>,
    val director: String,
    val seasons: List<Season>? = null,
    val episodes: List<Episode>? = null,
    val isPremium: Boolean = false,
    val isDownloaded: Boolean = false,
    val watchProgress: Float = 0f, // 0.0 to 1.0
    val lastWatchedPosition: Long = 0L, // in milliseconds
    val dateAdded: Long = System.currentTimeMillis(),
    val language: String = "en",
    val quality: List<String> = listOf("720p", "1080p")
) : Parcelable

@Parcelize
data class CastMember(
    val name: String,
    val character: String,
    val profileUrl: String
) : Parcelable

@Parcelize
data class Season(
    val seasonNumber: Int,
    val title: String,
    val episodeCount: Int,
    val releaseYear: Int,
    val posterUrl: String
) : Parcelable

@Parcelize
data class Episode(
    val episodeNumber: Int,
    val seasonNumber: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val thumbnailUrl: String,
    val videoUrl: String,
    val releaseDate: String,
    val rating: Float
) : Parcelable

enum class ContentType {
    MOVIE,
    TV_SHOW,
    LIVE_TV,
    SPORTS,
    SHORT_VIDEO
}

@Parcelize
data class Category(
    val id: String,
    val name: String,
    val contentType: ContentType,
    val itemCount: Int
) : Parcelable

@Parcelize
data class SearchHistory(
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class UserProfile(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String?,
    val isPremium: Boolean = false,
    val createdAt: Long
) : Parcelable