package ir.hoseinahmadi.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.hoseinahmadi.myapplication.data.model.BottomNavigationItem
import ir.hoseinahmadi.myapplication.navigatin.BottomNavigation
import ir.hoseinahmadi.myapplication.navigatin.NavGraph
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.ui.component.AlertDialogSendMessage
import ir.hoseinahmadi.myapplication.ui.component.AppConfig
import ir.hoseinahmadi.myapplication.ui.component.ChangeStatusBarColor
import ir.hoseinahmadi.myapplication.ui.component.showAlertMessage
import ir.hoseinahmadi.myapplication.ui.theme.DaneshjooyarTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppConfig()
            navHostController = rememberNavController()
            ChangeStatusBarColor(navHostController)
            DaneshjooyarTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    val item = listOf<BottomNavigationItem>(
                        BottomNavigationItem(
                            route = Screen.Home.route,
                            name = "خانه",
                            selectedIcon = painterResource(id = R.drawable.home),
                            unSelectedIcon = painterResource(id = R.drawable.homefiil)
                        ),
                        BottomNavigationItem(
                            route = Screen.About.route,
                            name = "درباره ما",
                            selectedIcon = painterResource(id = R.drawable.about),
                            unSelectedIcon = painterResource(id = R.drawable.aboutfiil)
                        ),
                        BottomNavigationItem(
                            route = Screen.Documents.route,
                            name = "مدارک",
                            selectedIcon = painterResource(id = R.drawable.doc),
                            unSelectedIcon = painterResource(id = R.drawable.docfiil)
                        ),

                        )
                    val backStackEntry = navHostController.currentBackStackEntryAsState()
                    val show = backStackEntry.value?.destination?.route in item.map { it.route }
                    Scaffold(
                        modifier = Modifier.fillMaxSize().systemBarsPadding(),
                        containerColor = Color.White,
                        topBar = {
                            AnimatedVisibility(
                                visible = show,
                                enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                                exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
                            ) {
                                TopBar()
                            }
                        },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = show,
                                enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                                exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
                            ) {
                                BottomNavigation(
                                    navHostController = navHostController,
                                    item = item,
                                    backStackEntry = backStackEntry
                                )
                            }
                        }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(Color.White)
                        ) {
                            AlertDialogSendMessage()
                            NavGraph(navHostController = navHostController)
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun TopBar() {
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
                    onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.dot),
                        contentDescription = "",
                        Modifier.size(17.dp)
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
