package ir.hoseinahmadi.myapplication.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myapplication.data.model.CourseItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseItemCard(item: CourseItem) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = { }) {
        GlideImage(
            modifier = Modifier
                .padding(5.dp)
                .size(330.dp, 190.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = item.image,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(5.dp),
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Blue
            )
    }

}