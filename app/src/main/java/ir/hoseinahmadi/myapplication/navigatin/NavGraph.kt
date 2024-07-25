package ir.hoseinahmadi.myapplication.navigatin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.hoseinahmadi.myapplication.ui.screens.AboutScreen
import ir.hoseinahmadi.myapplication.ui.screens.DocumentsScreen
import ir.hoseinahmadi.myapplication.ui.screens.HomeScreen
import ir.hoseinahmadi.myapplication.ui.screens.SplashScreen
import ir.hoseinahmadi.myapplication.ui.screens.login.EnterPhoneScreen
import ir.hoseinahmadi.myapplication.ui.screens.login.VerifyPhoneScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navHostController = navHostController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Screen.EnterPhone.route) {
            EnterPhoneScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.VerifyPhone.route + "?phone={phone}",
            arguments = listOf(
                navArgument("phone") {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            VerifyPhoneScreen(
                navHostController = navHostController,
                phone = it.arguments?.getString("phone") ?: "null"
            )
        }

        composable(route = Screen.Documents.route) {
            DocumentsScreen(navHostController)
        }
         composable(route = Screen.About.route) {
             AboutScreen(navHostController)
        }

    }
}