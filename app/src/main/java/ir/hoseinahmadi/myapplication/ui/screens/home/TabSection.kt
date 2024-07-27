package ir.hoseinahmadi.myapplication.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import ir.hoseinahmadi.myapplication.data.model.HomeCategory
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabSection(
    pagerState: PagerState,
    tabItem: List<HomeCategory>
) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 15.dp, top = 5.dp),
        indicator = {},
        divider = {},
        selectedTabIndex = pagerState.currentPage
    ) {
        tabItem.fastMapIndexed { index, homeCategory ->
            CategoryItem(
                item = homeCategory,
                selected = pagerState.currentPage == index
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    item: HomeCategory,
    selected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (selected) Brush.linearGradient(listOf(startLinearGradient, endLinearGradient))
    else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(12.dp),)
            .background(tint)
            .height(40.dp),
        border = BorderStroke(1.dp, startLinearGradient.copy(0.7f)),
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = item.icon,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(30.dp)
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = if (selected) Color.White else startLinearGradient
            )
        }
    }
}