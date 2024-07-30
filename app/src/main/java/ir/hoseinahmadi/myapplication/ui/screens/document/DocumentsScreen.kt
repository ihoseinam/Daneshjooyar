package ir.hoseinahmadi.myapplication.ui.screens.document

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.data.model.CompletedItem
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.font_bold
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
    if (item.isEmpty()) {
        EmptyItem {
            navHostController.navigate(Screen.Home.route)
        }
    } else {
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

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CompletedItem(data: CompletedItem) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
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
                    .size(150.dp, 90.dp),
                contentScale = ContentScale.FillBounds
            )

            Text(
                modifier = Modifier.padding(end = 8.dp, start = 8.dp),
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
        Box(  modifier = Modifier
            .rotate(-8f)
            .align(Alignment.TopStart)
            .size(80.dp, 100.dp)){
            Image(painter = painterResource(id = R.drawable.completed_ic),
                contentDescription ="",
                contentScale = ContentScale.FillBounds,)
            Text(
                modifier = Modifier.rotate(14f).padding(top = 18.dp, start = 19.dp),
                text = "اتمام\n دوره",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = yekan_bold,
                    lineHeight = 19.sp
                    ),
                fontWeight = FontWeight.Black,
                color = Color.Black
                )
        }

    }

}

@Composable
fun EmptyItem(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        Image(
            painter = painterResource(id = R.drawable.empty),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(226.dp, 168.dp)
        )
        Text(
            text = "شما هنوز مدرکی ندارید\n" +
                    "چون دوره ای را به پایان نرسانده اید. ",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                Color.Transparent,
            ),
            shape = RoundedCornerShape(11.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 22.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(
                    Brush.linearGradient(listOf(startLinearGradient, endLinearGradient))
                ),
            onClick = onClick
        ) {
            Text(text = "مشاهده دوره ها",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
                )
        }

    }


}

