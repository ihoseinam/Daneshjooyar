package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myapplication.ui.screens.courseDetail.player.calculateWatchedPercentage
import ir.hoseinahmadi.myapplication.viewModel.CourseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayListItemCard(
    id: Int,
    image: String,
    title: String,
    onClick: () -> Unit,
    viewModel: CourseViewModel = hiltViewModel()
) {
    var watchedPercentage by remember { mutableFloatStateOf(0f) }
    var watchedRanges by remember { mutableStateOf<MutableList<Pair<Long, Long>>>(mutableListOf()) }
    val totalDuration = remember { mutableLongStateOf(0L) }

    LaunchedEffect(id) {
        launch {
            viewModel.getCourseItem(id).collectLatest { item ->
                totalDuration.longValue = item.totalDuration
                watchedPercentage = calculateWatchedPercentage(watchedRanges, totalDuration.longValue)
            }
        }
        launch {
            viewModel.getWatchedRanges(id).collectLatest { ranges ->
                watchedRanges = ranges.toMutableList()
                watchedPercentage = calculateWatchedPercentage(watchedRanges, totalDuration.longValue)
            }
        }

    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (watchedPercentage.roundToInt()>=100)Color.Green else Color.White
        ),
        elevation = CardDefaults.cardElevation(0.4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlideImage(
                model = image,
                contentDescription = "video thumbnail",
                modifier = Modifier
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .height(70.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 13.sp
                ),
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }
        Text(text = watchedPercentage.toString())

      /*  HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray.copy(0.5f)
        )*/
    }

}