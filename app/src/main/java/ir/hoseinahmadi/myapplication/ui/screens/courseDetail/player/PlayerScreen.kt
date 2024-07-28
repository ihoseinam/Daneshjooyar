package ir.hoseinahmadi.myapplication.ui.screens.courseDetail.player

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.media3.common.Player
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.cache.VideoPlayerCacheManager
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem
import ir.hoseinahmadi.myapplication.data.model.CourseSection
import ir.hoseinahmadi.myapplication.ui.screens.courseDetail.TopBar
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.utils.Helper
import ir.hoseinahmadi.myapplication.viewModel.CourseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@SuppressLint("MutableCollectionMutableState")
@Composable
fun PlayerScreen(
    navHostController: NavHostController,
    videoIndex: Int, data: String,
    viewModel: CourseViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val context = LocalContext.current as Activity
    VideoPlayerCacheManager.initialize(context, 1024 * 1024 * 1024) // 1GB

    var item by remember { mutableStateOf<List<CourseSection>>(emptyList()) }
    val listTypeToken = object : TypeToken<List<CourseSection>>() {}.type
    item = Gson().fromJson(data, listTypeToken)

    var playerVideoIndex by remember { mutableIntStateOf(videoIndex) }
    val urlPlayer = item[playerVideoIndex].videoUri

    var watchedRanges by remember { mutableStateOf<MutableList<Pair<Long, Long>>>(mutableListOf()) }
    var currentStartTime by remember { mutableStateOf<Long?>(null) }
    val totalDuration = remember { mutableLongStateOf(0L) }
    var isPlaying by remember { mutableStateOf(true) }
    var previousTime by remember { mutableLongStateOf(0L) }
    val currentCourseId = item[playerVideoIndex].id

    var watchedPercentage by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(playerVideoIndex) {
        viewModel.getWatchedRanges(currentCourseId).collectLatest { ranges ->
            watchedRanges = ranges.toMutableList()
            watchedPercentage = calculateWatchedPercentage(watchedRanges, totalDuration.longValue)
        }
    }


    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle
    val currentStartTimeState = rememberUpdatedState(currentStartTime)
    val watchedRangesState = rememberUpdatedState(watchedRanges)
    val previousTimeState = rememberUpdatedState(previousTime)
    val currentCourseIdState = rememberUpdatedState(currentCourseId)
    val totalDurationState = rememberUpdatedState(totalDuration)

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                currentStartTimeState.value?.let { start ->
                    addWatchedRange(watchedRangesState.value, start, previousTimeState.value)
                    currentStartTime = null
                }
                viewModel.upsertCourseItem(
                    currentCourseIdState.value,
                    watchedRangesState.value,
                    totalDurationState.value.longValue
                )
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    DisposableEffect(playerVideoIndex) {
        onDispose {
            currentStartTime?.let { start ->
                addWatchedRange(watchedRanges, start, previousTime)
                currentStartTime = null
            }
            viewModel.upsertCourseItem(currentCourseId, watchedRanges, totalDuration.longValue)
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            if (orientation != Configuration.ORIENTATION_LANDSCAPE){
                TopBar { navHostController.navigateUp() }
            }
        },
        bottomBar = {
            if (orientation != Configuration.ORIENTATION_LANDSCAPE){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 2.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val enableBackButton = playerVideoIndex > 0
                    val enableForwardButton = playerVideoIndex < item.size - 1
                    OutlinedButton(
                        border = BorderStroke(
                            1.dp,
                            color = if (enableBackButton) startLinearGradient else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = enableBackButton,
                        onClick = {
                            if (playerVideoIndex > 0) {
                                playerVideoIndex--
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = "",
                                modifier = Modifier.size(17.dp),
                                tint = if (enableBackButton) startLinearGradient else Color.LightGray
                            )
                            Text(
                                text = "قسمت قبلی",
                                color = if (enableBackButton) startLinearGradient else Color.LightGray,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    OutlinedButton(
                        border = BorderStroke(
                            1.dp,
                            color = if (enableForwardButton) startLinearGradient else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = enableForwardButton,
                        onClick = {
                            if (playerVideoIndex < item.size - 1) {
                                playerVideoIndex++
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "قسمت بعدی",
                                color = if (enableForwardButton) startLinearGradient else Color.LightGray,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = "",
                                modifier = Modifier
                                    .rotate(180f)
                                    .size(17.dp),
                                tint = if (enableForwardButton) startLinearGradient else Color.LightGray
                            )
                        }
                    }
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
                Text(
                    text = "${Helper.byLocate((playerVideoIndex + 1).toString())}. ${item[playerVideoIndex].title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                )
            }

            // Video player setup
            VideoPlayer(
                mediaItems = listOf<VideoPlayerMediaItem>(
                    VideoPlayerMediaItem.NetworkMediaItem(urlPlayer)
                ),
                handleLifecycle = true,
                autoPlay = true,
                usePlayerController = true,
                enablePip = false,
                handleAudioFocus = true,
                enablePipWhenBackPressed = false,
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
                repeatMode = RepeatMode.NONE,
                onCurrentTimeChanged = { currentTime ->
                    if (isPlaying) {
                        if (currentStartTime == null) {
                            currentStartTime = currentTime
                        }

                        if (currentTime < previousTime) {
                            currentStartTime?.let { start ->
                                addWatchedRange(watchedRanges, start, previousTime)
                            }
                            currentStartTime = null
                        } else {
                            val threshold = 1000L
                            if (currentTime - (currentStartTime ?: 0) > threshold) {
                                currentStartTime?.let { start ->
                                    addWatchedRange(watchedRanges, start, currentTime)
                                    currentStartTime = currentTime
                                }
                            }
                        }
                        previousTime = currentTime
                        watchedPercentage =
                            calculateWatchedPercentage(watchedRanges, totalDuration.longValue)
                    }
                },
                fullScreenSecurePolicy = SecureFlagPolicy.SecureOn,
                playerInstance = {
                    addListener(object : Player.Listener {
                        override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                            isPlaying = isPlayingNow
                            if (!isPlayingNow) {
                                currentStartTime?.let { start ->
                                    addWatchedRange(watchedRanges, start, previousTime)
                                    currentStartTime = null
                                }
                            }
                        }

                        override fun onPlaybackStateChanged(state: Int) {
                            if (state == Player.STATE_READY) {
                                totalDuration.longValue = duration
                                watchedPercentage = calculateWatchedPercentage(
                                    watchedRanges,
                                    totalDuration.longValue
                                )
                            }
                        }
                    })
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))

                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .then(
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Modifier.wrapContentHeight()
                        } else {
                            Modifier.height(210.dp)
                        }
                    )
            )


            Slider(
                enabled = false,
                value = watchedPercentage , // Convert percentage to a value between 0 and 1
                onValueChange = {},
                valueRange = 0f..100f
            )
            Text(text =(watchedPercentage).roundToInt().toString() +"%" )
        }
    }
}


fun addWatchedRange(ranges: MutableList<Pair<Long, Long>>, start: Long, end: Long) {
    if (start >= end) return

    var newRange = start to end
    val updatedRanges = mutableListOf<Pair<Long, Long>>()

    var inserted = false
    for (range in ranges) {
        if (newRange.second < range.first) {
            if (!inserted) {
                updatedRanges.add(newRange)
                inserted = true
            }
            updatedRanges.add(range)
        } else if (newRange.first > range.second) {
            updatedRanges.add(range)
        } else {
            newRange =
                minOf(newRange.first, range.first) to maxOf(newRange.second, range.second)
        }
    }

    if (!inserted) {
        updatedRanges.add(newRange)
    }

    ranges.clear()
    ranges.addAll(updatedRanges)
}

fun calculateWatchedPercentage(ranges: List<Pair<Long, Long>>, totalDuration: Long): Float {
    if (totalDuration <= 0) return 0f

    val watchedDuration = ranges.sumOf { (start, end) -> end - start }
    return minOf((watchedDuration.toFloat() / totalDuration) * 100, 100f)
}
