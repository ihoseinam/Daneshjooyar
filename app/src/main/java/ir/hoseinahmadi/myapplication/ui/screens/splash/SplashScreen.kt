package ir.hoseinahmadi.myapplication.ui.screens.splash

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        if (isOnline(context)) {
            delay(1000)
            navigateToHome(navHostController)
        } else {
            showDialog = true
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xff50ABFF),
                        Color(0xff000080),
                    )
                )
            )
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(400.dp, 205.dp),
            painter = painterResource(id = R.drawable.splash_top),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(400.dp, 205.dp),
            painter = painterResource(id = R.drawable.splash_bottom),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Image(
            modifier = Modifier
                .size(140.dp, 111.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = ""
        )
        if (!isOnline(context)) {
            OutlinedButton(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .align(Alignment.BottomCenter),
                border = BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(11.dp),
                onClick = {
                    if (isOnline(context)) {
                        navigateToHome(navHostController)
                    } else {
                        showDialog = true
                    }
                }) {
                Text(
                    text = "تلاش مجدد",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

    }

    BottomSheetNoInternet(showDialog) {
        showDialog = false
    }


}


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun navigateToHome(navHostController: NavHostController) {
    if (Constants.CHECK_LOGIN) {
        navHostController.navigate(Screen.Home.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    } else {
        navHostController.navigate(Screen.EnterPhone.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }
}

