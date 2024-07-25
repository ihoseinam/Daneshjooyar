package ir.hoseinahmadi.myapplication.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.myapplication.ui.theme.endLinearGradient
import ir.hoseinahmadi.myapplication.ui.theme.font_standard
import ir.hoseinahmadi.myapplication.ui.theme.startLinearGradient

@Composable
fun TextAndUnderLine(
    fullText: String,
    underlinedText: List<String>,
    style :TextStyle =MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight = FontWeight.SemiBold
) {
    Text(
        text = buildAnnotatedString {
            append(fullText)
            underlinedText.forEach {
                val startIndex = fullText.indexOf(it)
                val endIndex = startIndex + it.length
                addStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xff3785CD)
                    ),
                    start = startIndex,
                    end = endIndex
                )
                addStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontFamily = font_standard,
                        ),
                    start = 0,
                    end = fullText.length
                )

            }
        },
        modifier = Modifier.padding(8.dp),
        style =style ,
        fontWeight = fontWeight,
        color = Color.DarkGray
    )

}