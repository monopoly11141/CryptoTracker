package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinDataSource: CoinDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            loadCoinList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _eventChannel = Channel<CoinListEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when(action) {
            is CoinListAction.onCoinClick -> {

            }

        }
    }

    private fun loadCoinList() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            coinDataSource
                .getCoinList()
                .onSuccess { coinList ->
                    _state.update{
                        it.copy(
                            isLoading = false,
                            coinList = coinList.map { coin -> coin.toCoinUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }

                    _eventChannel.send(CoinListEvent.Error(error))
                }
        }
    }

}