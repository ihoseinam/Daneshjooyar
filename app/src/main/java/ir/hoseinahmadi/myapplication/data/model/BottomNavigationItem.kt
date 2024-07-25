package ir.hoseinahmadi.myapplication.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val route:String,
    val name:String,
    val selectedIcon: Painter,
    val unSelectedIcon: Painter,
)
