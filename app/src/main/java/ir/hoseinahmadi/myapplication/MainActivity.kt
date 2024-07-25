package ir.hoseinahmadi.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.hoseinahmadi.myapplication.data.model.BottomNavigationItem
import ir.hoseinahmadi.myapplication.navigatin.BottomNavigation
import ir.hoseinahmadi.myapplication.navigatin.NavGraph
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.ui.component.AppConfig
import ir.hoseinahmadi.myapplication.ui.theme.DaneshjooyarTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppConfig()
            navHostController = rememberNavController()
            DaneshjooyarTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    val item = listOf<BottomNavigationItem>(
                        BottomNavigationItem(
                            route = Screen.Home.route,
                            name = "خانه",
                            selectedIcon = painterResource(id = R.drawable.home_fill),
                            unSelectedIcon = painterResource(id = R.drawable.home)
                        ),
                        BottomNavigationItem(
                            route = Screen.About.route,
                            name = "درباره ما",
                            selectedIcon = painterResource(id = R.drawable.about_fill),
                            unSelectedIcon = painterResource(id = R.drawable.about)
                        ),
                        BottomNavigationItem(
                            route = Screen.Documents.route,
                            name = "مدارک",
                            selectedIcon = painterResource(id = R.drawable.documents_fill),
                            unSelectedIcon = painterResource(id = R.drawable.documents)
                        ),

                        )
                    val backStackEntry = navHostController.currentBackStackEntryAsState()
                    val show = backStackEntry.value?.destination?.route in item.map { it.route }
                    Scaffold(
                        containerColor = Color.White,
                        bottomBar = {
                            AnimatedVisibility(
                                visible = show,
                                enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                                exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
                            ) {
                                BottomNavigation(
                                    navHostController = navHostController,
                                    item = item,
                                    backStackEntry = backStackEntry
                                    )
                            }
                        }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(Color.White)
                        ) {
                            NavGraph(navHostController = navHostController)
                        }
                    }
                }

            }
        }
    }
}
