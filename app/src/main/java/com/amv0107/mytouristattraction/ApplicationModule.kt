package com.amv0107.mytouristattraction

import com.amv0107.mytouristattraction.data.Repository
import com.amv0107.mytouristattraction.data.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun getRetrofitClient() = RetrofitClient()

    @Provides
    @Singleton
    fun getRepository(retrofitClient: RetrofitClient) = Repository(retrofitClient)
}