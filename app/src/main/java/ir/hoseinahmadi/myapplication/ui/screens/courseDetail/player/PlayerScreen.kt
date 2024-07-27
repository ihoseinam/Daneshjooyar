package ir.hoseinahmadi.myapplication.ui.screens.courseDetail.player

import android.app.Activity
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.cache.VideoPlayerCacheManager
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import ir.hoseinahmadi.myapplication.data.model.CourseSection
import ir.hoseinahmadi.myapplication.ui.screens.courseDetail.TopBar
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.utils.Helper
import ir.hoseinahmadi.myapplication.viewModel.CourseViewModel
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PlayerScreen(
    navHostController: NavHostController,
    data: String,
    videoIndex: Int = 0,
    viewModel: CourseViewModel = hiltViewModel() // Pass the ViewModel
) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val context = LocalContext.current as Activity
//    VideoPlayerCacheManager.initialize(context, 1024 * 1024 * 1024) // 1GB

    var item by remember {
        mutableStateOf<List<CourseSection>>(emptyList())
    }
    val listTypeToken = object : TypeToken<List<CourseSection>>() {}.type
    item = Gson().fromJson(data, listTypeToken)

    var playerVideoIndex by remember {
        mutableIntStateOf(videoIndex)
    }
    val urlPlayer = item[playerVideoIndex].videoUri

    val watchedRanges = remember { mutableStateListOf<Pair<Long, Long>>() }
    var currentStartTime by remember { mutableStateOf<Long?>(null) }
    val totalDuration = remember { mutableLongStateOf(0L) }
    var isPlaying by remember { mutableStateOf(true) }
    var previousTime by remember { mutableLongStateOf(0L) } // Track the previous time

    // Remember the current course item ID
    val currentCourseId = item[playerVideoIndex].id

    // State to hold the initial watched percentage
    var initialWatchedPercentage by remember { mutableFloatStateOf(0f) }

    // Fetch the watched percentage from the database
    LaunchedEffect(playerVideoIndex) {
        viewModel.getWatchRange(currentCourseId).collectLatest { watchedPercentage ->
            initialWatchedPercentage = watchedPercentage
        }
    }

    // Effect to save the watched percentage before the screen closes or video changes
    DisposableEffect(playerVideoIndex) {
        onDispose {
            // Calculate the watched percentage
            val watchedPercentage = calculateWatchedPercentage(watchedRanges, totalDuration.longValue)

            // Save to the database
            viewModel.upsertCourseItem(CourseItemDb(id = currentCourseId, watchDuration = watchedPercentage))
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            if (orientation != Configuration.ORIENTATION_LANDSCAPE)
                TopBar {
                    navHostController.navigateUp()
                }
        },
        bottomBar = {
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "${Helper.byLocate((playerVideoIndex + 1).toString())}. ${item[playerVideoIndex].title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 12.dp),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
            )
            VideoPlayer(
                mediaItems = listOf<VideoPlayerMediaItem>(
                    VideoPlayerMediaItem.NetworkMediaItem(urlPlayer)
                ),
                handleLifecycle = true,
                autoPlay = true,
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
                repeatMode = RepeatMode.NONE,
                onCurrentTimeChanged = { currentTime ->
                    Log.e("CurrentTime", currentTime.toString())

                    if (isPlaying) {
                        if (currentStartTime == null) {
                            currentStartTime = currentTime
                        }

                        // Detect backward seek
                        if (currentTime < previousTime) {
                            // Finalize the current watching segment before the backward seek
                            currentStartTime?.let { start ->
                                addWatchedRange(watchedRanges, start, previousTime)
                            }
                            currentStartTime = null
                        } else {
                            // Threshold time to consider as "watched" (1 second = 1000ms)
                            val threshold = 1000L

                            // Update watched ranges
                            if (currentTime - (currentStartTime ?: 0) > threshold) {
                                currentStartTime?.let { start ->
                                    addWatchedRange(watchedRanges, start, currentTime)
                                    currentStartTime = currentTime
                                }
                            }
                        }
                        previousTime = currentTime
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
                            }
                        }
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .then(
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Modifier.fillMaxHeight()
                        } else {
                            Modifier.height(210.dp)
                        }
                    )
            )

            val watchedPercentage = calculateWatchedPercentage(watchedRanges, totalDuration.longValue)
            Text(text = "Watched: ${initialWatchedPercentage}%")
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                text = stringResource(id = R.string.lorm),
                textAlign = TextAlign.Start,
                color = Color.DarkGray,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

fun addWatchedRange(ranges: MutableList<Pair<Long, Long>>, start: Long, end: Long) {
    val newRange = start to end
    val overlappingRanges = mutableListOf<Pair<Long, Long>>()

    for (range in ranges) {
        if (newRange.first <= range.second && newRange.second >= range.first) {
            overlappingRanges.add(range)
        }
    }

    ranges.removeAll(overlappingRanges)

    val mergedStart =
        minOf(newRange.first, overlappingRanges.minOfOrNull { it.first } ?: newRange.first)
    val mergedEnd =
        maxOf(newRange.second, overlappingRanges.maxOfOrNull { it.second } ?: newRange.second)

    ranges.add(mergedStart to mergedEnd)

    ranges.sortBy { it.first }
}

fun calculateWatchedPercentage(ranges: List<Pair<Long, Long>>, totalDuration: Long): Float {
    val watchedDuration = ranges.sumOf { it.second - it.first }
    return minOf((watchedDuration.toFloat() / totalDuration) * 100, 100f)
}