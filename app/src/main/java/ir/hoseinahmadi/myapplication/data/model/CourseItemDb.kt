package ir.hoseinahmadi.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseItemDb(
    @PrimaryKey val id: Int=-5,
    var watchedRanges: String = "[]",
    var totalDuration: Long = 0L
)
