package com.totalwar.warhammer.ui.screen.faction.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.navigation.AppScreens
import com.totalwar.warhammer.ui.theme.ColorOnPrimary

@Composable
fun FactionCard(
    faction: FactionsQuery.Faction,
    navController: NavController,
    gameVersion: String,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick ?: {
                navController.navigate(
                    AppScreens.FactionUnitsScreen.routeWithArgs(faction.key.toString())
                )
            })
            .paint(
                painter = painterResource(R.drawable.unit_background),
                contentScale = ContentScale.FillBounds
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/${gameVersion}/${faction.flags_url}/mon_64.webp"
                ),
                contentDescription = "Flag of ${faction.subculture?.name.orEmpty()}",
                modifier = Modifier.size(130.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = faction.subculture?.name.orEmpty(),
                color = ColorOnPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
