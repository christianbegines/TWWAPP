package com.totalwar.warhammer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
        viewModel.getGameVersion()
        setContent {
            val gameVersion: GameVersionsQuery.Version? by viewModel.gameVersion.observeAsState(
                initial = null
            )
            if (gameVersion?.id?.isBlank() == false) {
                viewModel.setGameVersionInSettings(gameVersion?.id.orEmpty())
                TotalWarhammerAppTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        AppScreen(viewModel, viewModel.dataStore)
                    }
                }
            }
        }
    }
}
