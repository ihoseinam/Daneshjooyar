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
fun MobileCategory(navHostController: NavHostController) {

    val item = listOf<CourseItem>(
        CourseItem(
            id = 1,
            name = "اندروید",
            title = "جامع ترین دوره آموزش برنامه نویسی اندروید (۱۵۰ ساعت با پشتیبانی ۲۴ ساعته)",
            image = "https://www.daneshjooyar.com/wp-content/uploads/2023/08/android-1-min-1-511x312.jpg",
            introductionVideo = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/Demo-Android.mp4",
            section = listOf<CourseSection>(
                CourseSection(
                    id = 20,
                    title = "دقیقا میشه بگین تو این دوره چی قراره یاد بگیرم؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/What1.mp4",
                ),
                  CourseSection(
                    id = 21,
                    title = "اندروید چیست و چرا برنامه نویسی اندروید را یاد بگیریم؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/S00-Part02-what-is-android.mp4",
                ),
              CourseSection(
                    id = 22,
                    title = "مسیر و نقشه راه برنامه نویسی موبایل (چگونه برنامه نویس موبایل شویم؟)",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Android-Programming/present/S00-Part03-android-path.mp4",
                ),
                CourseSection(
                    id = 23,
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
            introductionVideo = "",
            section = listOf<CourseSection>(
                CourseSection(
                    id = 30,
                    title = "کسب و کار اینترنتی چیست؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/free-business/S01-Part01-what-is-business.mp4"
                ),
                  CourseSection(
                    id = 32,
                    title = "چه چیزهایی را در کسب و کار اینترنتی میتوان فروخت؟",
                    videoUri = "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/free-business/S01-Part01-what-is-business.mp4"
                ),

            )
        ),
       /*   CourseItem(
            id = 2,
            name = "",
            title = "",
            image = "",
            introductionVideo = "",
            section = listOf<CourseSection>()
        ),
             CourseItem(
            id = 2,
            name = "",
            title = "",
            image = "",
            introductionVideo = "",
            section = listOf<CourseSection>()
        ),
        */
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(item) {courseItem->
            CourseItemCard(courseItem){
                val data =Gson().toJson(courseItem)
                navHostController.navigate(Screen.CourseDetail.route+"?data=$data")
            }
        }
    }

}