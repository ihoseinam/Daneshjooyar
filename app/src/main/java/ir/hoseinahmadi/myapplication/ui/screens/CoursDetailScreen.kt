package ir.hoseinahmadi.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.hoseinahmadi.myapplication.data.model.CourseItem
import ir.hoseinahmadi.myapplication.ui.screens.home.player.StreamerPlayer
import ir.hoseinahmadi.myapplication.viewModel.VideoPlayerViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseDetailScreen(
    navHostController: NavHostController,
    data: String
) {
    var item by remember {
        mutableStateOf<CourseItem>(CourseItem())
    }

    val listTypeToken = object : TypeToken<CourseItem>() {}.type
    item = Gson().fromJson(data, listTypeToken)

    var isPlaying by remember {
        mutableStateOf(false)
    }
    var videoItemIndex by remember {
        mutableIntStateOf(0)
    }
    val viewModel: VideoPlayerViewModel = viewModel()
    viewModel.videoList = item.section
    val context = LocalContext.current

    Column {
        StreamerPlayer(
            viewModel = viewModel,
            isPlaying = isPlaying,
            onPlayerClosed = { isVideoPlaying ->
                isPlaying = isVideoPlaying
            })
        LazyColumn(Modifier.padding(10.dp), content = {
            itemsIndexed(items = item.section) { index, items ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (videoItemIndex != index) isPlaying = false
                            viewModel.index = index
                            videoItemIndex = viewModel.index
                        },
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(model = item.image, contentDescription = "video thumbnail")
                    Text(
                        text =items.title,
                        Modifier
                            .weight(1f)
                    )
                }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }
        })
        LaunchedEffect(key1 = videoItemIndex) {
            isPlaying = true
            viewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo()
            }
        }
    }

}