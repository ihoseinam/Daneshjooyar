package ir.hoseinahmadi.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hoseinahmadi.myapplication.data.remote.SendMessageApiInterFace
import ir.hoseinahmadi.myapplication.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetWorkModule(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BUSE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideSendMessageApiInterFace(retrofit: Retrofit): SendMessageApiInterFace =
        retrofit.create(SendMessageApiInterFace::class.java)
}