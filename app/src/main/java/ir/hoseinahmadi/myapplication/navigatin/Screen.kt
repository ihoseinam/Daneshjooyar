package ir.hoseinahmadi.myapplication.navigatin

sealed class Screen(val route:String) {
    data object Home:Screen("home")
    data object Splash:Screen("splash")
    data object EnterPhone:Screen("EnterPhone")
    data object VerifyPhone:Screen("VerifyPhone")
    data object Documents:Screen("Documents")
    data object About:Screen("About")
    data object CourseDetail:Screen("CourseDetail")
}