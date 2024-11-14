package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.cryptotracker.crypto.presentation.coin_list.component.CoinListItem
import com.plcoding.cryptotracker.crypto.presentation.coin_list.component.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreenRoot(
    viewModel: CoinListViewModel = hiltViewModel()
) {
    CoinListScreen(state = viewModel.state.collectAsStateWithLifecycle().value)
}

@Composable
fun CoinListScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    if(state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coinList) { coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HorizontalDivider()
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListState(
                coinList = (1..100).map {
                    previewCoin.copy(
                        id = it.toString()
                    )
                }
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )

    }
}