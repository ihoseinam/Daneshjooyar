package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
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
import ir.hoseinahmadi.myapplication.data.model.CompletedItem
import ir.hoseinahmadi.myapplication.data.model.CourseItem
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import ir.hoseinahmadi.myapplication.data.model.CourseSection
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.ui.component.IsCompletedCourse
import ir.hoseinahmadi.myapplication.ui.component.showAlertMessage
import ir.hoseinahmadi.myapplication.utils.Helper
import ir.hoseinahmadi.myapplication.viewModel.CompletedViewModel
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
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
    val pagerState = rememberPagerState { 2 }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            if (orientation != Configuration.ORIENTATION_LANDSCAPE)
                TopBar {
                    navHostController.navigateUp()
                }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (!isPlaying) {
                TrailerImage(image = item.image) {
                    isPlaying = true
                }
            } else {
                VideoTrailer(item.introductionVideo, orientation)
            }
            CourseTabRow(pagerState)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pagState ->
                when (pagState) {
                    0 -> InfoTeacher()
                    else -> VideoList(item, navHostController)
                }

            }
        }
    }

}

@Composable
fun VideoTrailer(video: String, orientation: Int) {
    VideoPlayer(
        mediaItems = listOf<VideoPlayerMediaItem>(
            VideoPlayerMediaItem.NetworkMediaItem(video)
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
        repeatMode = RepeatMode.NONE,
        onCurrentTimeChanged = {
            Log.e("CurrentTime", it.toString())
        },
        enablePipWhenBackPressed = false,
        fullScreenSecurePolicy = SecureFlagPolicy.SecureOn, // فعال کردن حالت ایمن
        playerInstance = {
            addAnalyticsListener(
                object : AnalyticsListener {

                }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .then(
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Modifier.fillMaxHeight()
                } else {
                    Modifier.height(210.dp)
                }
            )
    )
}

@Composable
fun VideoList(
    data: CourseItem,
    navHostController: NavHostController,
    viewModel: CompletedViewModel = hiltViewModel()
) {
    var watchedPercentages by remember { mutableStateOf<Map<Int, Float>>(emptyMap()) }
    val totalWatchedPercentage = watchedPercentages.values.sum()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (totalWatchedPercentage.roundToInt() >= (data.section.size) * 95) {
            item {
                IsCompletedCourse(data.name)
            }
        }
        itemsIndexed(items = data.section) { index, item ->
            PlayListItemCard(
                id = item.id,
                image = data.image,
                title = "${Helper.byLocate((index + 1).toString())}. ${item.title}",
                onClick = {
                    val senData = Gson().toJson(data.section)
                    navHostController.navigate(Screen.Player.route + "?data=$senData?index=$index")
                },
                watchDuration = {
                    watchedPercentages = watchedPercentages.toMutableMap().apply {
                        put(item.id, it)
                    }
                }
            )
        }
    }
   LaunchedEffect(watchedPercentages) {
         if (totalWatchedPercentage.roundToInt() >= ((data.section.size) * 95)) {
             viewModel.upsertCompletedItem(
                 CompletedItem(
                     id = data.id,
                     image = data.image,
                     title = data.title
                 )
             )
         }
     }

}


@Composable
fun TopBar(onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .padding(horizontal = 8.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp, 30.dp)
            )


            IconButton(
                onClick = {
                    showAlertMessage.value =true
                }) {
                Image(
                    painter = painterResource(id = R.drawable.support),
                    contentDescription = "",
                    Modifier.size(24.dp)

                )
            }

        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray.copy(0.6f)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
