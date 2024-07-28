package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseTabRow(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val title = listOf(
        "اطلاعات",
        "ویدیو ها",
    )
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp),
        divider = {},
        indicator = {tabPosition->
            Box(modifier = Modifier
                .tabIndicatorOffset(tabPosition[pagerState.currentPage])
                .fillMaxWidth()
                .height(2.dp)
                .background(startLinearGradient.copy(0.9f))
            )
        },
        selectedTabIndex = pagerState.currentPage
    ) {
        title.fastMapIndexed { index, s ->
            val selected =pagerState.currentPage == index
            Tab(
                modifier = Modifier.background(Color.White),
                selected =selected ,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index, animationSpec = tween(500)) }
                },
                text = {
                    Text(
                        text = s,
                        style = MaterialTheme.typography.titleMedium,
                        color =if (selected) Color.Black else Color.DarkGray.copy(0.8f),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )

        }
    }

}