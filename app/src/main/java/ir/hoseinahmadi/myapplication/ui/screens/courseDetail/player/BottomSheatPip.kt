package ir.hoseinahmadi.myapplication.ui.screens.courseDetail.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.utils.Constants
import ir.hoseinahmadi.myapplication.viewModel.DatStoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetPip(
    datStoreViewModel: DatStoreViewModel = hiltViewModel(),
    enablePip:(Boolean)->Unit,
    onDismissRequest: () -> Unit
) {
    var enable by remember {
        mutableStateOf(Constants.USER_PIP)
    }
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = onDismissRequest
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .padding(bottom = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "فعال کردن پنجره شناور",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Switch(
                colors = SwitchDefaults.colors(
                    checkedTrackColor = endLinearGradient,
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.DarkGray,
                    checkedBorderColor = Color.Transparent,
                    uncheckedTrackColor = Color.White,
                    uncheckedBorderColor = Color.DarkGray
                ),
                checked = enable,
                onCheckedChange = {
                    Constants.USER_PIP = it
                    enablePip(it)
                    enable = it
                    datStoreViewModel.saveEnablePip(it)
                }
            )
        }

    }
}