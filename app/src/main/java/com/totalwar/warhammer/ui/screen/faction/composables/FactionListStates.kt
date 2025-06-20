import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.ui.screen.faction.composables.FactionCard

@Composable
fun FactionErrorState(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Failed to load factions.")
            Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun FactionLoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Welcome! Loading factions soon...")
    }
}

@Composable
fun FactionSuccessState(
    factions: List<FactionsQuery.Faction>,
    gameVersion: String,
    navController: NavController,
    gridState: LazyGridState
) {
    if (factions.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No factions available.")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(vertical = 4.dp),
            state = gridState
        ) {
            items(factions, key = { it.key ?: it.hashCode() }) { faction ->
                FactionCard(
                    faction = faction,
                    navController = navController,
                    gameVersion = gameVersion
                )
            }
        }
    }

}

