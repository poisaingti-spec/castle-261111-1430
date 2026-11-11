package com.crafted.castle.data.local

import androidx.room.*
import com.crafted.castle.data.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistory)
    
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 20")
    fun getSearchHistory(): Flow<List<SearchHistory>>
    
    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()
    
    @Query("DELETE FROM search_history WHERE query = :query")
    suspend fun deleteSearchHistory(query: String)
    
    @Query("SELECT * FROM search_history WHERE query LIKE :query || '%' ORDER BY timestamp DESC LIMIT 10")
    suspend fun getSearchSuggestions(query: String): List<SearchHistory>
}