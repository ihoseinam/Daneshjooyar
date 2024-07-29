package ir.hoseinahmadi.myapplication.ui.screens.home.cat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.gson.Gson
import ir.hoseinahmadi.myapplication.data.model.CourseItem
import ir.hoseinahmadi.myapplication.data.model.CourseSection
import ir.hoseinahmadi.myapplication.navigatin.Screen

@Composable
fun GameCategory(navHostController: NavHostController) {

    val item = listOf<CourseItem>(
        CourseItem(
            id = 5,
            name = "جت پک کامپوز",
            title = "کامل ترین آموزش جت پک کامپوز برای طراحی UI در اندروید",
            image = "https://www.daneshjooyar.com/wp-content/uploads/2023/04/JAT-PACK-min-1-511x312.jpg",
            introductionVideo = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/main/S19-Part01-what-is-compose.mp4",
            section = listOf<CourseSection>(
                CourseSection(
                    id =50 ,
                    title = "جت پک کامپوز چیست؟",
                    videoUri ="https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/jetpack-compose/S20-Part01-what-is-compose.mp4"
                ),
                CourseSection(
                    id =51 ,
                    title = "تفاوت کتابخانه جت پک با کامپوز",
                    videoUri ="https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/jetpack-compose/S20-Part02-Jetpack-And-Compose.mp4"
                ),

                )
        ),
        CourseItem(
            id = 1,
            name = "اندروید",
            title = "جامع ترین دوره آموزش برنامه نویسی اندروید (۱۵۰ ساعت با پشتیبانی ۲۴ ساعته)",
            image = "https://www.daneshjooyar.com/wp-content/uploads/2023/08/android-1-min-1-511x312.jpg",
            introductionVideo = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/Demo-Android.mp4",
            section = listOf<CourseSection>(
                CourseSection(
                    id = 10,
                    title = "دقیقا میشه بگین تو این دوره چی قراره یاد بگیرم؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/What1.mp4",
                ),
                CourseSection(
                    id = 11,
                    title = "اندروید چیست و چرا برنامه نویسی اندروید را یاد بگیریم؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/S00-Part02-what-is-android.mp4",
                ),
                CourseSection(
                    id = 12,
                    title = "مسیر و نقشه راه برنامه نویسی موبایل (چگونه برنامه نویس موبایل شویم؟)",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/S00-Part03-android-path.mp4",
                ),
                CourseSection(
                    id = 13,
                    title = "کاتلین یا جاوا؟ چرا زبان کاتلین بهتر است؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/S00-Part05-kotlin-or-java.mp4",
                ),
            )
        ),
        CourseItem(
            id = 2,
            name = " کسب و کار اینترنتی",
            title = "دوره آموزش کسب و کار اینترنتی \uD83E\uDDD1\u200D\uD83D\uDCBB (رایگان و کاربردی)",
            image = "https://www.daneshjooyar.com/wp-content/uploads/2024/05/Online-Businesss-511x312.png",
            introductionVideo = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/free-business/S01-Part00-intro.mp4",
            section = listOf<CourseSection>(
                CourseSection(
                    id = 20,
                    title = "کسب و کار اینترنتی چیست؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/free-business/S01-Part01-what-is-business.mp4"
                ),
                CourseSection(
                    id = 21,
                    title = "چه چیزهایی را در کسب و کار اینترنتی میتوان فروخت؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/free-business/S01-Part02-products.mp4"
                ),

                )
        ),
        CourseItem(
            id = 3,
            name = "المنتور",
            title = "دوره آموزش المنتور رایگان به همراه ۲ پروژه عملی",
            image = "https://www.daneshjooyar.com/wp-content/uploads/2024/02/elementor-min-511x312.png",
            introductionVideo = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/elementor-free/intro.mp4",
            section = listOf<CourseSection>(
                CourseSection(
                    id = 30,
                    title = "المنتور چیست؟ چرا باید از آن استفاده کنیم؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Business/S05-Elementor-free-and-pro/S05-Part01-what-is-elementor.mp4"
                ),

                )
        ),
        CourseItem(
            id = 4,
            name = "طراحی سایت",
            title = "آموزش رایگان طراحی سایت بدون کدنویسی از صفر مطلق⚡\uFE0F",
            image = "https://www.daneshjooyar.com/wp-content/uploads/2023/02/wordpress-min-511x312.jpg",
            introductionVideo = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/wordpress-advanced/intro.mp4",
            section = listOf<CourseSection>(
                CourseSection(
                    id = 40,
                    title = "معرفی دوره آموزش طراحی سایت بدون کدنویسی با وردپرس",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/wordpress-advanced/intro.mp4"
                ),
                CourseSection(
                    id = 41,
                    title = "ویژگی های طراحی سایت بدون کدنویسی با طراحی سایت اختصاصی",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/wordpress-advanced/site-design-code.mp4"
                ),
                CourseSection(
                    id = 42,
                    title = "تاریخچه پیدایش اینترنت",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Business/S02-Concept/S02-Part01-internet-history.mp4"
                ),

                )
        ),



        )
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(item) { courseItem ->
            CourseItemCard(courseItem) {
                val data = Gson().toJson(courseItem)
                navHostController.navigate(Screen.CourseDetail.route + "?data=$data")
            }
        }
    }
}