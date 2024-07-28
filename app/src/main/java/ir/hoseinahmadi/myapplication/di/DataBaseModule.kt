package ir.hoseinahmadi.myapplication.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hoseinahmadi.myapplication.data.db.CompletedDao
import ir.hoseinahmadi.myapplication.data.db.CourseDao
import ir.hoseinahmadi.myapplication.data.db.CourseDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(
            context = context,
            name = "my_db",
            klass = CourseDataBase::class.java
        ).build()

    @Provides
    @Singleton
    fun provideCourseDao(dataBase: CourseDataBase):CourseDao = dataBase.CourseDao()

    @Provides
    @Singleton
    fun provideCompletedDao(dataBase: CourseDataBase):CompletedDao = dataBase.CompletedDao()

}