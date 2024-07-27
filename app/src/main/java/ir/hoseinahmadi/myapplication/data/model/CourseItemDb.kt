package ir.hoseinahmadi.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseItemDb(
    @PrimaryKey
    val id:Int,
    var watchDuration :Float=0f
)