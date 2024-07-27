package ir.hoseinahmadi.myapplication.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb

@Dao
interface CourseDao {
    @Upsert
    suspend fun upsertCourseItem(itemDb: CourseItemDb)


}