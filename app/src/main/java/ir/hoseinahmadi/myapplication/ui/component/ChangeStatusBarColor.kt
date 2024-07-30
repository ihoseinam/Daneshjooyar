package ir.hoseinahmadi.myapplication.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient

@Composable
fun ChangeStatusBarColor(navHostController: NavHostController) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val systemUiController = rememberSystemUiController()




    when (navBackStackEntry?.destination?.route) {
        Screen.Splash.route -> {
            SideEffect {
                systemUiController.apply {
                    setStatusBarColor(color = Color(0xff5a97e8))
                    setNavigationBarColor(color = Color(0xFF4265c1))
                }
            }

        }

        else -> {
            SideEffect {
                systemUiController.apply {
                    setStatusBarColor(color = Color.White)
                    setNavigationBarColor(color = Color.White)
                }
            }
        }
    }

}