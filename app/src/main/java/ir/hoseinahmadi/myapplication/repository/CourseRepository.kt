package ir.hoseinahmadi.myapplication.repository

import ir.hoseinahmadi.myapplication.data.db.CourseDao
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val dao: CourseDao
) {

     fun getWatchRange(id: Int): Flow<Float> = dao.getWatchRange(id)

    suspend fun upsertCourseItem(itemDb: CourseItemDb) {
        dao.upsertCourseItem(itemDb)
    }

}