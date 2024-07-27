package ir.hoseinahmadi.myapplication.ui.screens.courseDetail.player

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.navigation.NavHostController
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@Composable
fun PlayerScreen(
    navHostController: NavHostController,
    data: String,
    videoIndex: Int,
) {

    VideoPlayer(
        mediaItems = listOf<VideoPlayerMediaItem>(
            VideoPlayerMediaItem.NetworkMediaItem(
                data
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
            .wrapContentHeight()
    )

}