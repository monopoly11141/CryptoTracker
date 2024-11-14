package com.plcoding.cryptotracker.di

import com.plcoding.cryptotracker.core.data.networking.HttpClientFactory
import com.plcoding.cryptotracker.crypto.data.networking.KtorCoinDataSource
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClientFactory.create(CIO.create())
    }

    @Provides
    @Singleton
    fun providesCoinDataSource(httpClient: HttpClient): CoinDataSource {
        return KtorCoinDataSource(httpClient)
    }

}