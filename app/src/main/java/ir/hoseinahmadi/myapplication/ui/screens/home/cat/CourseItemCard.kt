package ir.hoseinahmadi.myapplication.ui.screens.home.cat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myapplication.data.model.CourseItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseItemCard(item: CourseItem,onClick :()->Unit) {
    Card(
        modifier = Modifier.padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = onClick) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(330.dp, 190.dp),
                model = item.image,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp
                ),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }

}