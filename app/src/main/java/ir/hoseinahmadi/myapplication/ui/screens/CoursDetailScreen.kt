package ir.hoseinahmadi.myapplication.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.cache.VideoPlayerCacheManager
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem
import ir.hoseinahmadi.myapplication.data.model.CourseItem
import ir.hoseinahmadi.myapplication.ui.screens.home.player.StreamerPlayer
import ir.hoseinahmadi.myapplication.viewModel.VideoPlayerViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseDetailScreen(
    navHostController: NavHostController,
    data: String
) {

    val context = LocalContext.current as Activity
    VideoPlayerCacheManager.initialize(context, 1024 * 1024 * 1024) // 1GB

    val viewModel: VideoPlayerViewModel = viewModel()

    var item by remember {
        mutableStateOf<CourseItem>(CourseItem())
    }

    val listTypeToken = object : TypeToken<CourseItem>() {}.type
    item = Gson().fromJson(data, listTypeToken)

    var isPlaying by remember { mutableStateOf(false) }
    var videoItemUrl by remember { mutableStateOf(item.introductionVideo) }

    Column {
        VideoPlayer(
            mediaItems = listOf<VideoPlayerMediaItem>(
                VideoPlayerMediaItem.NetworkMediaItem(
                    videoItemUrl
                )
            ),
            handleLifecycle = true,
            autoPlay = false,
            usePlayerController = true,
            enablePip = true,
            handleAudioFocus = true,
     /*       onFullScreenEnter = {
                context.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
            },
            onFullScreenExit = {
                context.requestedOrientation = SCREEN_ORIENTATION_USER
            },*/
            controllerConfig = VideoPlayerControllerConfig(
                showSpeedAndPitchOverlay = true,
                showSubtitleButton = true,
                showCurrentTimeAndTotalTime = true,
                showBufferingProgress = true,
                showForwardIncrementButton = true,
                showBackwardIncrementButton = true,
                showBackTrackButton = true,
                showNextTrackButton = true,
                showRepeatModeButton = true,
                controllerShowTimeMilliSeconds = 5_000,
                showFullScreenButton = true,
                controllerAutoShow = true,
            ),
            volume = 0.5f,  // volume 0.0f to 1.0f
            repeatMode = RepeatMode.ALL,       // or RepeatMode.ALL, RepeatMode.ONE
            onCurrentTimeChanged = { // long type, current player time (millisec)
                Log.e("CurrentTime", it.toString())
            },
            enablePipWhenBackPressed = true,
            playerInstance = { // ExoPlayer instance (Experimental)
                addAnalyticsListener(
                    object : AnalyticsListener {
                        // player logger
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyColumn(Modifier.padding(10.dp)) {
            itemsIndexed(items = item.section) { index, items ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            videoItemUrl = items.videoUri
                        },
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(model = item.image, contentDescription = "video thumbnail")
                    Text(
                        text = items.title,
                        Modifier.weight(1f)
                    )
                }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }
        }
    }
}