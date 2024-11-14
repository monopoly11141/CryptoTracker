package com.plcoding.cryptotracker.crypto.data.networking

import com.plcoding.cryptotracker.core.data.networking.constructUrl
import com.plcoding.cryptotracker.core.data.networking.safeCall
import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.mapper.toCoin
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinListResponseDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {

    override suspend fun getCoinList(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinListResponseDto> {
            httpClient.get(
                urlString = constructUrl(
                    url = "/assets"
                )
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

}