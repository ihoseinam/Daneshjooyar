package ir.hoseinahmadi.myapplication.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.yekan_bold
import ir.hoseinahmadi.myapplication.viewModel.MessageViewModel
import kotlinx.coroutines.flow.collectLatest

val showAlertMessage = mutableStateOf(false)

@Composable
fun AlertDialogSendMessage(
    viewModel: MessageViewModel = hiltViewModel()
) {
    val show by remember {
        showAlertMessage
    }
    if (show) {
        var title by remember {
            mutableStateOf("")
        }

        var body by remember {
            mutableStateOf("")
        }

        var checkedInput by remember {
            mutableStateOf(false)
        }
        var loading by remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        LaunchedEffect(true) {
            viewModel.notifeResponse.collectLatest {
                if (it.success == 1) {
                    Toast.makeText(context, "پیام با موفقیت ارسال شد", Toast.LENGTH_SHORT).show()
                    loading = false
                    showAlertMessage.value = false
                    viewModel.resetResponse()
                }
            }


        }
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = {
                showAlertMessage.value = false
                viewModel.resetResponse()
            },
            confirmButton = { /*TODO*/ },

            title = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onClick = {
                            showAlertMessage.value = false
                            viewModel.resetResponse()

                        }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }

                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "ارسال پیام به پشتیبانی",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = yekan_bold
                        ),
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedLabelColor = Color.DarkGray,
                            errorTextColor = Color(0xffED2E2E),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray,
                            focusedIndicatorColor = Color(0xff000080).copy(0.6f),
                            focusedLabelColor = Color(0xff000080),
                            errorIndicatorColor = Color(0xffED2E2E),
                            errorSupportingTextColor = Color(0xffED2E2E),
                            errorLabelColor = Color(0xffED2E2E),
                            unfocusedIndicatorColor = Color(0xffC4C4C4),
                            errorContainerColor = Color.White
                        ),
                        isError = (checkedInput && title.isEmpty()),
                        maxLines = 1,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        shape = RoundedCornerShape(9.dp),
                        value = title, onValueChange = { title = it },
                        label = {
                            Text(
                                text = "عنوان",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        })
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        modifier = Modifier.height(130.dp),
                        isError = (checkedInput && body.isEmpty()),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        colors = TextFieldDefaults.colors(
                            unfocusedLabelColor = Color.DarkGray,
                            errorTextColor = Color(0xffED2E2E),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray,
                            focusedIndicatorColor = Color(0xff000080).copy(0.6f),
                            focusedLabelColor = Color(0xff000080),
                            errorIndicatorColor = Color(0xffED2E2E),
                            errorSupportingTextColor = Color(0xffED2E2E),
                            errorLabelColor = Color(0xffED2E2E),
                            unfocusedIndicatorColor = Color(0xffC4C4C4),
                            errorContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(9.dp),
                        value = body, onValueChange = {
                            body = it
                        },
                        label = {
                            Text(
                                text = "متن پیام...",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        })

                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(11.dp))
                            .background(
                                Brush.linearGradient(listOf(startLinearGradient, endLinearGradient))
                            ),
                        onClick = {
                            if (title.isNotEmpty() && body.isNotEmpty()) {
                                loading = true
                                checkedInput = false
                                val message =
                                    "پیام جدید از دانشجویار :\n\nموضوع : $title\n متن پیام : \n $body "
                                viewModel.senMessage(message)
                            } else {
                                checkedInput = true
                            }
                        }) {
                        if (loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Text(
                                text = "ارسال",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                    }


                }

            }
        )
    }


}