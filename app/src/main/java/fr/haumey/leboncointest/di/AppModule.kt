package fr.haumey.leboncointest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.haumey.leboncointest.BuildConfig
import fr.haumey.leboncointest.data.local.AppDatabase
import fr.haumey.leboncointest.data.local.TitleDao
import fr.haumey.leboncointest.data.remote.ListingService
import fr.haumey.leboncointest.data.remote.ListingSource
import fr.haumey.leboncointest.data.repository.ListingRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    private fun getBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .client(getHttpClient())
        .baseUrl(getBaseUrl())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideListingService(retrofit: Retrofit): ListingService = retrofit.create(ListingService::class.java)

    @Singleton
    @Provides
    fun provideListingRemoteDataSource(service: ListingService) = ListingSource(service)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideTitleDao(db: AppDatabase) = db.titleDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ListingSource, localDataSource: TitleDao) = ListingRepository(remoteDataSource, localDataSource)
}