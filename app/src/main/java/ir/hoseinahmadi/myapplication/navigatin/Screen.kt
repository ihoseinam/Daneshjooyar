package ir.hoseinahmadi.myapplication.navigatin

sealed class Screen(val route:String) {
    data object Home:Screen("home")
    data object Splash:Screen("splash")
    data object Login:Screen("login")

}