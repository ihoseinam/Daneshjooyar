package ir.hoseinahmadi.myapplication.ui.screens.courseDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
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
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp),
        divider = {},
        selectedTabIndex = pagerState.currentPage
    ) {
        title.fastMapIndexed { index, s ->
            val selected =pagerState.currentPage == index
            Tab(selected =selected ,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index) }
                },
                text = {
                    Text(
                        text = s,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            )

        }
    }

}