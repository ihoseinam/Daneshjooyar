package ir.hoseinahmadi.myapplication.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Upsert
    suspend fun upsertCourseItem(itemDb: CourseItemDb)

    @Query("select watchDuration from CourseItemDb where id=:id")
    fun getWatchRange(id: Int): Flow<Float>

}