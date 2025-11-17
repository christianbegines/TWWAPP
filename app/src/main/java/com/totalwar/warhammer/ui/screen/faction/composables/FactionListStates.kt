import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.ui.screen.faction.composables.FactionCard
import com.totalwar.warhammer.util.Constants

/**
 * Shows error state with specific error message
 */
@Composable
fun FactionErrorState(errorMessage: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Constants.UI.PADDING_LARGE)
        ) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error
            )
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = Constants.UI.PADDING_MEDIUM)
            ) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
fun FactionLoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Cargando facciones...",
            style = MaterialTheme.typography.bodyLarge
        )
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
            Text(
                "No hay facciones disponibles.",
                style = MaterialTheme.typography.bodyLarge
            )
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
