package ir.hoseinahmadi.myapplication.navigatin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myapplication.data.model.BottomNavigationItem
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient

@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    item: List<BottomNavigationItem>,
    backStackEntry: State<NavBackStackEntry?>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(horizontal = 8.dp)
            .background(Color.White),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        item.forEach { navItem ->
            val selected = navItem.route == backStackEntry.value?.destination?.route
            val tint =if (selected) startLinearGradient else Color.DarkGray
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                selected = selected,
                onClick = {
                    navHostController.navigateSingleTopTo(navItem.route)
                },
                icon = {
                    Icon(
                        painter = if (selected) navItem.selectedIcon else navItem.unSelectedIcon,
                        contentDescription = "",
                        tint = tint,
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = {
                    Text(text = navItem.name,
                        color = tint,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                        )
                }
                )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
            inclusive =true
        }
        launchSingleTop = true
        restoreState = true
    }