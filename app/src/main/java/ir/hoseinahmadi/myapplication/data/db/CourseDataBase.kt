package ir.hoseinahmadi.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.hoseinahmadi.myapplication.data.model.CompletedItem
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb

@Database(entities = [CourseItemDb::class,CompletedItem::class], version = 1, exportSchema = false)
abstract class CourseDataBase:RoomDatabase() {
    abstract fun CourseDao(): CourseDao
    abstract fun CompletedDao():CompletedDao
}