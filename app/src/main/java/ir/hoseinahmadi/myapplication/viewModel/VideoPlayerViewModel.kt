package ir.hoseinahmadi.myapplication.viewModel

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import ir.hoseinahmadi.myapplication.data.model.CourseSection

@OptIn(UnstableApi::class)
class VideoPlayerViewModel : ViewModel() {
    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer(context: Context) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }
    }

    fun releasePlayer() {
        exoPlayer?.playWhenReady = false
        exoPlayer?.release()
        exoPlayer = null
    }

    fun playVideo(videoUrl: String) {
        exoPlayer?.let { player ->
            player.apply {
                stop()
                clearMediaItems()
                setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
                playWhenReady = true
                prepare()
            }
        }
    }

    fun playerViewBuilder(context: Context): PlayerView {
        val activity = context as Activity
        return PlayerView(context).apply {
            player = exoPlayer
            controllerAutoShow = true
            keepScreenOn = true
            setFullscreenButtonClickListener { isFullScreen ->
                activity.requestedOrientation = if (isFullScreen) {
                    SCREEN_ORIENTATION_USER_LANDSCAPE
                } else {
                    SCREEN_ORIENTATION_USER
                }
            }
        }
    }
}
