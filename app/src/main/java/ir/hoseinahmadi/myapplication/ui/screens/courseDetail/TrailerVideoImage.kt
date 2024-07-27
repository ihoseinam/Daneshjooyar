package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrailerVideoImage(image: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(225.dp)
            .padding(8.dp)
            .border(1.dp, color = endLinearGradient, shape = RoundedCornerShape(16.dp))
    ) {
        GlideImage(
            model = image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(startLinearGradient, endLinearGradient)))
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)

                )
            }
        }

    }
}