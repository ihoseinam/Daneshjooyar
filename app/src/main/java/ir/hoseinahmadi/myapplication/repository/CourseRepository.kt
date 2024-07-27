package ir.hoseinahmadi.myapplication.repository

import ir.hoseinahmadi.myapplication.data.db.CourseDao
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val dao: CourseDao
) {

    suspend fun upsertCourseItem(itemDb: CourseItemDb){ dao.upsertCourseItem(itemDb) }

}