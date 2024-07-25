package ir.hoseinahmadi.myapplication.utils

import java.text.DecimalFormat

object Helper {
    fun byLocate(englishStr: String): String {
        var result = ""
        var fa = '۰'
        for (ch in englishStr) {
            fa = ch
            when (ch) {
                '0' -> fa = '۰'
                '1' -> fa = '۱'
                '2' -> fa = '۲'
                '3' -> fa = '۳'
                '4' -> fa = '۴'
                '5' -> fa = '۵'
                '6' -> fa = '۶'
                '7' -> fa = '۷'
                '8' -> fa = '۸'
                '9' -> fa = '۹'
            }
            result = "${result}$fa"
        }
        return result
    }

    fun bySeparator(price: String): String {
        val priceFormat = DecimalFormat("###,###")
        return priceFormat.format(Integer.valueOf(price))
    }


    fun byLocateAndSeparator(price: String): String {
        val priceWithoutCommas = price.replace(",", "")
        val persianDigit =byLocate(priceWithoutCommas)
        return bySeparator(persianDigit)
    }

}