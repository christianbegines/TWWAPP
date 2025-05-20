package com.totalwar.warhammer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.totalwar.warhammer.ui.theme.TotalWarhammerAppTheme
import com.totalwar.warhammer.viewmodels.AppViewModel
import com.totalwar.warhammer.views.AppScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gameVersion: GameVersionsQuery.Version? by viewModel.gameVersion.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()
            LaunchedEffect(Unit) {
                viewModel.getGameVersion()
            }
            if (isLoading) {
                TotalWarhammerAppTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            } else if (gameVersion?.id?.isNotBlank() == true) {
                LaunchedEffect(gameVersion?.id) {
                    viewModel.setGameVersionInSettings(gameVersion?.id.orEmpty())
                }
                TotalWarhammerAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        AppScreen()
                    }
                }
            }

        }
    }
}
