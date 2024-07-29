package ir.hoseinahmadi.myapplication.ui.screens.splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.yekan_bold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetNoInternet(
    show: Boolean,
    onDismissRequest: () -> Unit
) {
    if (show) {
        ModalBottomSheet(
            shape = RoundedCornerShape(11.dp),
            dragHandle = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp),
            containerColor = Color.White,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "اتصال به اینترنت",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = yekan_bold,
                        fontSize = 25.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.no_internet),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(210.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "به اینترنت متصل نیستید. برای استفاده از اپلیکیشن به اینترنت متصل شوید.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .width(180.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(11.dp),
                        onClick = { }) {
                        Text(
                            text = "اینترنت موبایل",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent,
                        ),
                        shape = RoundedCornerShape(11.dp),
                        modifier = Modifier
                            .width(180.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(11.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        startLinearGradient,
                                        endLinearGradient
                                    )
                                )
                            ),
                    ) {
                        Text(
                            text = "اینترنت Wi-Fi",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                }
            }
        }

    }
}
