package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.data.model.CourseItem
import ir.hoseinahmadi.myapplication.data.model.CourseSection


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
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
            ) {pagState->
                if (pagState==1){
                    VideoList(item.section,item.image)
                }else{
                    InfoTeacher()
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
                    Modifier.fillMaxHeight()
                } else {
                    Modifier.height(210.dp)
                }
                )
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoList(data:List<CourseSection>,image:String){
    LazyColumn(Modifier.padding(10.dp)) {
        itemsIndexed(items = data) { index, item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {},
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(model = image, contentDescription = "video thumbnail")
                Text(
                    text = item.title,
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


@Composable
private fun TopBar(onClick: () -> Unit) {
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
                onClick = { }) {
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