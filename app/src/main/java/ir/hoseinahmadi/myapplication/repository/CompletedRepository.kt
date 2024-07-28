package ir.hoseinahmadi.myapplication.repository

import ir.hoseinahmadi.myapplication.data.db.CompletedDao
import ir.hoseinahmadi.myapplication.data.model.CompletedItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompletedRepository @Inject constructor(
    private val dao: CompletedDao
) {
    suspend fun upsertCompletedItem(item: CompletedItem) {
        dao.upsertCompletedItem(item)
    }

    suspend fun getAllCompletedItem(): Flow<List<CompletedItem>> = dao.getAllCompletedItem()


}