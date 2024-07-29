package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.yekan_black
import ir.hoseinahmadi.myapplication.ui.theme.yekan_bold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoTeacher() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 20.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 4.dp,  bottom = 8.dp),
            text = "از کی یاد می\u200Cگیریم؟",
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = yekan_bold,
                fontSize = 24.sp,
                lineHeight = 37.sp,
            ),
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(listOf(startLinearGradient, endLinearGradient))),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.alirezapng),
                contentDescription = "",
                Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .size(150.dp, 185.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(top = 38.dp)
            ) {
                Text(
                    text = "علیرضا احمدی",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = yekan_black,
                        fontSize = 24.sp
                    ),
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Row(
                    modifier = Modifier.padding(top = 12.dp, start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.danlogo),
                        contentDescription = "",
                        Modifier
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .size(24.dp)
                    )
                    Text(
                        text = "مدیر وبسایت دانشجویار",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 8.dp, start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "",
                        Modifier
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .size(27.dp)
                    )
                    Text(
                        text = "مدرس برتر دانشجویار",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }

        }
        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoItem(
                icon = painterResource(id = R.drawable.profile),
                title = "۵۷,۷۰۳ نفر",
                description = "تعداد دانشجو"
            )
                 InfoItem(
                icon = painterResource(id = R.drawable.like),
                title = "۴.۵ از ۵",
                description = "امتیاز دانشجویان"
            )
                 InfoItem(
                icon = painterResource(id = R.drawable.dore),
                title = "۲۶ عدد",
                description = "تعداد دوره ها"
            )
                 InfoItem(
                icon = painterResource(id = R.drawable.clock),
                title = "۶۹۷ ساعت",
                description = "ساعت آموزش"
            )

        }

    }
}

@Composable
 fun InfoItem(
    icon: Painter,
    title: String,
    description: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 50.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = icon, contentDescription = "",
                Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    brush = Brush.linearGradient(listOf(startLinearGradient, endLinearGradient)),
                    fontFamily = yekan_bold
                ),
                fontWeight = FontWeight.Black,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
            )
        }
    }
}