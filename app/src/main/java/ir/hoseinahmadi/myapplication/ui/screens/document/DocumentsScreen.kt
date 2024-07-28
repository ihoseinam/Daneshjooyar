package ir.hoseinahmadi.myapplication.ui.screens.document

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myapplication.data.model.CompletedItem
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.yekan_bold
import ir.hoseinahmadi.myapplication.viewModel.CompletedViewModel

@Composable
fun DocumentsScreen(
    navHostController: NavHostController,
    viewModel: CompletedViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getAllCourse()
    }
    val item by viewModel.allCompletedItem.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(item) { course ->
            CompletedItem(course)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompletedItem(data: CompletedItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.linearGradient(listOf(startLinearGradient, endLinearGradient))),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = data.image,
            contentDescription = "",
            modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp, start = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .size(150.dp,90.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            modifier = Modifier.padding( end = 8.dp, start = 8.dp),
            text = data.title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = yekan_bold,
            ),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

    }
}

