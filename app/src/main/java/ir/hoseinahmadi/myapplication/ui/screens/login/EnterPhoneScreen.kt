package ir.hoseinahmadi.myapplication.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.ui.component.TextAndUnderLine
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.utils.Helper
import ir.hoseinahmadi.myapplication.utils.InputValidation

@Composable
fun EnterPhoneScreen(navHostController: NavHostController) {
    var phone by rememberSaveable {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(151.dp, 110.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Start),
            text = "ورود یا ثبت نام",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "لطفا شماره موبایل خود را وارد کنید.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            trailingIcon = {
                if (phone.isNotEmpty()) {
                    IconButton(onClick = {
                        phone = ""
                        isError = false
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "",
                            tint = Color(0xffC4C4C4)
                        )
                    }

                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(11.dp),
            isError = isError,
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors(
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
            modifier = Modifier.fillMaxWidth(),
            value = Helper.byLocate(phone),
            onValueChange = { phone = it },
            label = {
                Text(
                    text = "شماره موبایل",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            supportingText = {
                if (isError) {
                    Text(
                        text = "شماره موبایل نامعتبر است.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )
        val backGround = if (phone.isEmpty()) Brush.linearGradient(
            listOf(
                startLinearGradient.copy(0.2f),
                endLinearGradient.copy(0.2f)
            )
        )
        else Brush.linearGradient(
            listOf(
                startLinearGradient,
                endLinearGradient
            )
        )
        Button(
            enabled = phone.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent,
            ),
            shape = RoundedCornerShape(11.dp),
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(
                    backGround
                ),
            onClick = {
                if (!InputValidation.isValidPhoneNumber(phone)) {
                    isError = true
                } else {
                    isError = false

                }
            }) {
            Text(
                text = "تایید و ادامه",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        TextButton(onClick = {  }) {
            TextAndUnderLine(
                fullText = "شرایط و قوانین لرن آکادمی را مطالعه کرده\u200Cام و می\u200Cپذیرم.",
                underlinedText = listOf(
                    "شرایط و قوانین"
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }


    }


}