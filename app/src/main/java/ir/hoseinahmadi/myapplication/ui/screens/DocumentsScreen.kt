package ir.hoseinahmadi.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myapplication.data.model.CompletedItem
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.viewModel.CompletedViewModel

@Composable
fun DocumentsScreen(
    navHostController: NavHostController,
    viewModel: CompletedViewModel = hiltViewModel()
) {
    Text(text = "Documents")

}

@Composable
fun CompletedItem(data:CompletedItem) {

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(8.dp)
            .background(Brush.linearGradient(listOf(startLinearGradient, endLinearGradient)))
    ) {

    }
}

