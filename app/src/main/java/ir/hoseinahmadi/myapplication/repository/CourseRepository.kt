package ir.hoseinahmadi.myapplication.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.hoseinahmadi.myapplication.data.db.CourseDao
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val dao: CourseDao
) {
    fun getWatchedRanges(id: Int): Flow<List<Pair<Long, Long>>> =
        dao.getWatchedRanges(id).map { json ->
            deserializeWatchedRanges(json)
        }

    suspend fun upsertCourseItem(itemDb: CourseItemDb) {
        dao.upsertCourseItem(itemDb)
    }

    private fun deserializeWatchedRanges(watchedRanges: String): List<Pair<Long, Long>> {
        val type = object : TypeToken<List<Pair<Long, Long>>>() {}.type
        return Gson().fromJson(watchedRanges, type) ?: emptyList()
    }

    fun serializeWatchedRanges(watchedRanges: List<Pair<Long, Long>>): String {
        return Gson().toJson(watchedRanges)
    }
}
