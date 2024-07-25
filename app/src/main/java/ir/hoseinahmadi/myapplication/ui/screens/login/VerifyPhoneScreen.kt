package ir.hoseinahmadi.myapplication.ui.screens.login

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myapplication.R
import ir.hoseinahmadi.myapplication.navigatin.Screen
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient
import ir.hoseinahmadi.myapplication.utils.Constants
import ir.hoseinahmadi.myapplication.utils.Helper
import ir.hoseinahmadi.myapplication.viewModel.DatStoreViewModel

@Composable
fun VerifyPhoneScreen(
    navHostController: NavHostController,
    phone: String,
    datStoreViewModel: DatStoreViewModel = hiltViewModel()
) {
    Text(
        text = Helper.byLocate(phone),
        modifier = Modifier.padding(12.dp)
    )

    var code by rememberSaveable {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(151.dp, 110.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Column(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 6.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 5.dp),
                text = "کد تایید ۵ رقمی را وارد کنید.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                text = "کد تایید برای شماره موبایل ${Helper.byLocate(phone)} ارسال شد.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }

        TextButton(
            modifier = Modifier.align(Alignment.Start),
            onClick = { navHostController.navigateUp() }) {
            Image(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "",
                Modifier
                    .padding(end = 8.dp)
                    .size(22.dp)
            )
            Text(
                text = "ویرایش شماره",
                color = startLinearGradient,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Surface(
            shape = RoundedCornerShape(11.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            color = White,
            border = BorderStroke(
                1.dp,
                color = if (isError) Color(0xffED2E2E) else Color(0xff000080).copy(0.6f)
            )
        ) {
            OtpInputField(
                otpLength = 5,
                code = Helper.byLocate(code),
                onCodeChange = { newCode -> code = newCode },
                isError = isError
            )
        }
        if (isError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 4.dp)
                    .align(Alignment.Start),
                text = "کد تایید وارد شده نامعتبر است.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xffED2E2E)
            )
        }

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
            enabled = code.isNotEmpty() || !isError,
            colors = ButtonDefaults.buttonColors(
                Color.Transparent,
            ),
            shape = RoundedCornerShape(11.dp),
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 6.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(
                    backGround
                ),
            onClick = {
                if (Helper.byLocate(code) != Helper.byLocate("12345")) {
                    isError = true
                } else {
                    isError = false
                    navHostController.navigate(Screen.Home.route)
                    Constants.CHECK_LOGIN = true
                    datStoreViewModel.saveIsLogin(true)
                }
            }) {
            Text(
                text = "ورود",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        TextButton(onClick = { }) {
            Text(
                text = "ارسال مجدد کد",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xff3785CD)
            )
        }


    }

}

@Composable
fun OtpInputField(
    otpLength: Int,
    code: String,
    onCodeChange: (String) -> Unit,
    isError: Boolean
) {
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            repeat(otpLength) { index ->
                OtpChar(
                    isError = isError,
                    index = index,
                    code = code,
                    onCodeChange = onCodeChange,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .focusRequester(focusRequesters[index]),
                    focusRequesters = focusRequesters
                )
            }
        }
    }

}

@Composable
fun OtpChar(
    index: Int,
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusRequesters: List<FocusRequester>,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    val char = if (index < code.length) code[index].toString() else ""

    TextField(
        isError = isError,
        value = char,
        onValueChange = { newChar ->
            if (newChar.length <= 1 && newChar.matches(Regex("\\d*"))) {
                val newOtp = code.toMutableList()
                if (newChar.isEmpty()) {
                    if (index < newOtp.size) newOtp.removeAt(index)
                } else {
                    if (index < newOtp.size) {
                        newOtp[index] = newChar[0]
                    } else {
                        newOtp.add(newChar[0])
                    }
                }
                onCodeChange(newOtp.joinToString(""))

                if (newChar.isNotEmpty() && index < focusRequesters.size - 1) {
                    focusRequesters[index + 1].requestFocus()
                } else if (newChar.isEmpty() && index > 0) {
                    focusRequesters[index - 1].requestFocus()
                }
            }
        },
        modifier = modifier
            .width(45.dp)
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Backspace && char.isEmpty() && index > 0) {
                    focusManager.moveFocus(FocusDirection.Previous)
                    true
                } else {
                    false
                }
            },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.End
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray,
            focusedIndicatorColor = Color(0xff000080).copy(0.6f),
            unfocusedIndicatorColor = Color(0xffC4C4C4),
            errorIndicatorColor = Color(0xffC4C4C4),
            errorContainerColor = Color.White
        ),
    )
}


