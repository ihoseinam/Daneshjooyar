package ir.hoseinahmadi.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {
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

    }
    LaunchedEffect(key1 = true) {
        delay(2000)
        if (Constants.CHECK_LOGIN){
            navHostController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }else{
            navHostController.navigate(Screen.EnterPhone.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }

    }
}
