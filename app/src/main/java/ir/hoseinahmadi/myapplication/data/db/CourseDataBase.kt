package ir.hoseinahmadi.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CourseDataBase::class], version = 1, exportSchema = false)
abstract class CourseDataBase:RoomDatabase() {
    abstract fun CourseDao(): CourseDao
}