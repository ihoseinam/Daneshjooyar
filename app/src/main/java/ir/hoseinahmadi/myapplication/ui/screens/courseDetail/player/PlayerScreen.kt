package ir.hoseinahmadi.myapplication.ui.screens.courseDetail.player

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.cache.VideoPlayerCacheManager
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.data.model.CourseSection
import ir.hoseinahmadi.myapplication.ui.screens.courseDetail.TopBar
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.yekan_bold
import ir.hoseinahmadi.myapplication.utils.Constants
import ir.hoseinahmadi.myapplication.utils.Helper
import ir.hoseinahmadi.myapplication.viewModel.CourseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
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

    var enablePip by remember {
        mutableStateOf(Constants.USER_PIP)
    }
    var showBottom by remember {
        mutableStateOf(false)
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            AnimatedVisibility(
                visible = orientation != Configuration.ORIENTATION_LANDSCAPE,
                enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
            ) {
                TopBar { navHostController.navigateUp() }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = orientation != Configuration.ORIENTATION_LANDSCAPE,
                enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
            ) {
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
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = yekan_bold
                    ),
                    fontWeight = FontWeight.Black,
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
                enablePip = true,
                handleAudioFocus = true,
                enablePipWhenBackPressed = enablePip,
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
                    .then(
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Modifier
                                .fillMaxSize()
                                .weight(1f)
                        } else {
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .height(210.dp)
                                .clip(RoundedCornerShape(16.dp))

                        }
                    )
            )
            if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
                val sliderWatch by animateFloatAsState(
                    targetValue = watchedPercentage.roundToInt().toFloat(), label = "",
                    animationSpec = tween(800)
                )


                if (showBottom) {
                    BottomSheetPip(
                        enablePip = { enablePip = it }
                    ) {
                        showBottom = false
                    }
                }

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(id = R.string.lorm),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(top = 9.dp, start = 12.dp),
                    text = "درصد پیشرفت",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = yekan_bold
                    ),
                    fontWeight = FontWeight.Black,
                )
                Text(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .align(Alignment.End),
                    text = "${
                        Helper.byLocate(
                            watchedPercentage.roundToInt().toString()
                        )
                    }/${Helper.byLocate("100%")}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .rotate(180f),
                    enabled = false,
                    value = sliderWatch,
                    onValueChange = {},
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        disabledActiveTrackColor = endLinearGradient,
                        disabledInactiveTrackColor = Color.LightGray.copy(0.35f),
                    ),
                    thumb = {
                        Image(
                            painter = painterResource(id = R.drawable.slidertum),
                            contentDescription = "",
                            modifier = Modifier
                                .rotate(180f)
                                .size(45.dp, 50.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                )
                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        showBottom = true
                    }
                ) {
                    Text(
                        text = "پنجره شناور",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color =if (enablePip)Color(0xFF19961E) else Color(0xFFCD1717)
                    )
                    if (enablePip) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "",
                            tint = Color(0xFF19961E)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "",
                            tint = Color(0xFFCD1717)
                        )
                    }

                }
            }

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
