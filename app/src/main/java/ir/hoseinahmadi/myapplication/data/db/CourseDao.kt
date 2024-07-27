package ir.hoseinahmadi.myapplication.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT watchedRanges FROM CourseItemDb WHERE id = :id")
    fun getWatchedRanges(id: Int): Flow<String>

    @Upsert
    suspend fun upsertCourseItem(courseItemDb: CourseItemDb)

    @Query("SELECT * FROM CourseItemDb WHERE id = :id")
    suspend fun getCourseItem(id: Int): CourseItemDb?
}