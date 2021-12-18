package com.yunuskocgurbuz.jetpackcomposemoviesapp.dependencyinjection

import com.yunuskocgurbuz.jetpackcomposemoviesapp.repository.MoviesRepository
import com.yunuskocgurbuz.jetpackcomposemoviesapp.service.MoviesAPI
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        api: MoviesAPI
    ) = MoviesRepository(api)

    @Singleton
    @Provides
    fun provideNewsApi(): MoviesAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesAPI::class.java)
    }
}