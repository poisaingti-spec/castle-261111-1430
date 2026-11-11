package com.crafted.castle.data.api

import com.crafted.castle.data.model.ApiResponse
import com.crafted.castle.data.model.Content
import com.crafted.castle.data.model.ContentListResponse
import com.crafted.castle.data.model.Category
import com.crafted.castle.data.model.ConfigResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentApi {
    
    @GET("config")
    suspend fun getConfig(): ApiResponse<ConfigResponse>
    
    @GET("content/home")
    suspend fun getHomeContent(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): ApiResponse<ContentListResponse>
    
    @GET("content/trending")
    suspend fun getTrending(
        @Query("type") type: String = "all",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): ApiResponse<ContentListResponse>
    
    @GET("content/new-releases")
    suspend fun getNewReleases(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): ApiResponse<ContentListResponse>
    
    @GET("content/top-rated")
    suspend fun getTopRated(
        @Query("type") type: String = "all",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): ApiResponse<ContentListResponse>
    
    @GET("content/movies")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("genre") genre: String? = null
    ): ApiResponse<ContentListResponse>
    
    @GET("content/tv-shows")
    suspend fun getTvShows(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("genre") genre: String? = null
    ): ApiResponse<ContentListResponse>
    
    @GET("content/sports")
    suspend fun getSports(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): ApiResponse<ContentListResponse>
    
    @GET("content/live-tv")
    suspend fun getLiveTV(
        @Query("category") category: String? = null
    ): ApiResponse<List<Content>>
    
    @GET("content/{contentId}")
    suspend fun getContentDetail(
        @Path("contentId") contentId: String
    ): ApiResponse<Content>
    
    @GET("content/{contentId}/episodes")
    suspend fun getEpisodes(
        @Path("contentId") contentId: String,
        @Query("season") season: Int
    ): ApiResponse<List<Content>>
    
    @GET("content/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("type") type: String? = null
    ): ApiResponse<ContentListResponse>
    
    @GET("categories")
    suspend fun getCategories(): ApiResponse<List<Category>>
    
    @GET("content/recommended")
    suspend fun getRecommended(
        @Query("basedOn") contentId: String,
        @Query("limit") limit: Int = 10
    ): ApiResponse<List<Content>>
}