package ir.hoseinahmadi.myapplication.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.hoseinahmadi.myapplication.data.model.CompletedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletedDao {
    @Upsert
    suspend fun upsertCompletedItem(item: CompletedItem)

    @Query("SELECT * FROM CompletedItem")
    fun getAllCompletedItem(): Flow<List<CompletedItem>>
}