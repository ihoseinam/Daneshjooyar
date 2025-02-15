package ir.hoseinahmadi.myapplication.navigatin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.hoseinahmadi.myapplication.ui.screens.about.AboutScreen
import ir.hoseinahmadi.myapplication.ui.screens.courseDetail.CourseDetailScreen
import ir.hoseinahmadi.myapplication.ui.screens.document.DocumentsScreen
import ir.hoseinahmadi.myapplication.ui.screens.home.HomeScreen
import ir.hoseinahmadi.myapplication.ui.screens.splash.SplashScreen
import ir.hoseinahmadi.myapplication.ui.screens.courseDetail.player.PlayerScreen
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
        composable(
            Screen.CourseDetail.route + "?data={data}",
            arguments = listOf(navArgument("data") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            it.arguments?.getString("data")?.let { data ->
                CourseDetailScreen(
                    navHostController = navHostController,
                    data = data
                )
            }

        }
        composable(Screen.Player.route + "?data={data}?index={index}",
            arguments = listOf(
                navArgument("data") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            PlayerScreen(
                navHostController = navHostController,
                data = it.arguments?.getString("data") ?: "",
                videoIndex = it.arguments?.getInt("index") ?: 0
            )
        }
    }
}