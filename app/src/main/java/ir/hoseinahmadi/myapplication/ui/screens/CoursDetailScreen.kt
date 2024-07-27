package ir.hoseinahmadi.myapplication.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
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
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.viewModel.VideoPlayerViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseDetailScreen(
    navHostController: NavHostController,
    data: String
) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val context = LocalContext.current as Activity
    VideoPlayerCacheManager.initialize(context, 1024 * 1024 * 1024) // 1GB

    var item by remember {
        mutableStateOf<CourseItem>(CourseItem())
    }

    val listTypeToken = object : TypeToken<CourseItem>() {}.type
    item = Gson().fromJson(data, listTypeToken)

    var isPlaying by remember { mutableStateOf(false) }
    var videoItemUrl by remember { mutableStateOf(item.introductionVideo) }

    Column {
        if (!isPlaying) {
            TrailerVideoImage(image = item.image) {
                isPlaying = true
            }
        } else {
            VideoPlayer(
                mediaItems = listOf<VideoPlayerMediaItem>(
                    VideoPlayerMediaItem.NetworkMediaItem(
                        item.introductionVideo
                    )
                ),
                handleLifecycle = true,
                autoPlay = false,
                usePlayerController = true,
                enablePip = false,
                handleAudioFocus = true,
                controllerConfig = VideoPlayerControllerConfig(
                    showSpeedAndPitchOverlay = true,
                    showSubtitleButton = false,
                    showCurrentTimeAndTotalTime = true,
                    showBufferingProgress = true,
                    showForwardIncrementButton = true,
                    showBackwardIncrementButton = true,
                    showBackTrackButton = false,
                    showNextTrackButton = false,
                    showRepeatModeButton = false,
                    controllerShowTimeMilliSeconds = 5_000,
                    showFullScreenButton = true,
                    controllerAutoShow = true,
                ),
                volume = 0.6f,  // volume 0.0f to 1.0f
                repeatMode = RepeatMode.NONE,       // or RepeatMode.ALL, RepeatMode.ONE
                onCurrentTimeChanged = { // long type, current player time (millisec)
                    Log.e("CurrentTime", it.toString())
                },
                enablePipWhenBackPressed = true,
                fullScreenSecurePolicy = SecureFlagPolicy.SecureOn, // فعال کردن حالت ایمن
                playerInstance = { // ExoPlayer instance (Experimental)
                    addAnalyticsListener(
                        object : AnalyticsListener {
                            // player logger
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                        then (
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Modifier.wrapContentHeight()
                        } else {
                            Modifier.height(210.dp)
                        }
                        )
            )
        }
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun TrailerVideoImage(image: String, onClick: () -> Unit) {
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