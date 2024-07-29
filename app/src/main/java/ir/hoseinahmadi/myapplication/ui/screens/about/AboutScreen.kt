package ir.hoseinahmadi.myapplication.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.ui.screens.courseDetail.InfoItem
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.yekan_bold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.dan_larglogo),
            contentDescription = "",
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .height(110.dp),
        )
        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoItem(
                icon = painterResource(id = R.drawable.users),
                title = "۴۰۰,۰۰۰+",
                description = "تعداد کاربران"
            )
            InfoItem(
                icon = painterResource(id = R.drawable.folowers),
                title = "۲۵,۰۰۰+",
                description = "بازدید روزانه"
            )
            InfoItem(
                icon = painterResource(id = R.drawable.profile),
                title = "۵۷,۷۰۳ نفر",
                description = "تعداد دانشجو"
            )

            InfoItem(
                icon = painterResource(id = R.drawable.dore),
                title = "۲۶ عدد",
                description = "تعداد دوره ها"
            )

        }
        Text(
            text = "ویژگی ها",
            modifier = Modifier
                .padding(
                    start = 18.dp,
                    top = 30.dp,
                    bottom = 8.dp
                )
                .align(Alignment.Start),
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = yekan_bold,
                fontSize = 24.sp
            ),
            color = Color.Black,
            fontWeight = FontWeight.Black,

            )
        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Attributes(
                painter = painterResource(id = R.drawable.madrak),
                title = "مدرک دو زبانه"
            )
            Attributes(
                painter = painterResource(id = R.drawable.modares),
                title = "مدرسین مجرب"
            )
            Attributes(
                painter = painterResource(id = R.drawable.takhasos),
                title = "دوره\u200Cهای تخصص محور"
            )
            Attributes(
                painter = painterResource(id = R.drawable.kheirie),
                title = "خیریه آموزشی"
            )

        }
        Text(
            text = "شبکه های اجتماعی",
            modifier = Modifier
                .padding(
                    start = 18.dp,
                    top = 12.dp,
                    bottom = 8.dp
                )
                .align(Alignment.Start),
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = yekan_bold,
                fontSize = 24.sp
            ),
            color = Color.Black,
            fontWeight = FontWeight.Black,

            )
        SocialMedia()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Attributes(
    painter: Painter,
    title: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .width(170.dp)
                .height(120.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(vertical = 5.dp),
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = yekan_bold
            ),
            color = Color.Black,
            fontWeight = FontWeight.Black,
        )
    }
}

@Composable
fun SocialMedia() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(180.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xff5117A1),
                            Color(0xff810cab),
                            Color(0xffc22979),
                            Color(0xfff24138),
                            Color(0xfff24138),
                            Color(0xffffd50a),
                            Color(0xffffd50a),
                        )
                    )
                ),
        ) {
            Row(
                modifier = Modifier.padding( 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.insta),
                    contentDescription = "",
                    Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
                Text(text = "اینستاگرام",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                    )
            }
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(180.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xffCE1312),
                            Color(0xffFF5554),
                        )
                    )
                ),
        ) {
            Row(
                modifier = Modifier.padding( 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.yotube),
                    contentDescription = "",
                    Modifier.size(50.dp),
                )
                Text(text = "یوتیوب",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

    }

}