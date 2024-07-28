package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

@Composable
fun InfoTeacher() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "از کی یاد می\u200Cگیریم؟",
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = yekan_black
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
                modifier = Modifier.padding(top = 40.dp)
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
                    modifier = Modifier.padding(top = 8.dp, start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(id = R.drawable.danlogo),
                        contentDescription ="",
                        Modifier.padding(end = 8.dp).clip(CircleShape).size(24.dp)
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
                    Image(painter = painterResource(id = R.drawable.star),
                        contentDescription ="",
                        Modifier.padding(end = 8.dp).clip(CircleShape).size(27.dp)
                        )
                    Text(
                        text = "مدرس برتر دانشجویار",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun priview() {
    InfoTeacher()
}