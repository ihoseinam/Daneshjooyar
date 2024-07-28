package ir.hoseinahmadi.myapplication.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.ui.theme.yekan_black
import ir.hoseinahmadi.myapplication.ui.theme.yekan_bold

@Composable
fun IsCompletedCourse(
    name: String
) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFFA500))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.dastpng),
            contentDescription = "",
            modifier = Modifier
                .size(130.dp),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 9.dp),
                text = "تبریـــــــــــــــــــــــــــــــــــــک!",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = yekan_black,
                    fontSize = 25.sp
                ),
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Surface(
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .padding(8.dp),
                color = Color.Transparent, // رنگ پس‌زمینه‌ی Surface
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "شما آموزش $name را به پایان رسانده‌اید.",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = yekan_bold,
                        fontSize = 20.sp,
                        lineHeight = 31.sp,
                    ),
                    textAlign = TextAlign.Start
                )

            }
        }

    }

}

