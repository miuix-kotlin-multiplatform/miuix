package top.yukonga.miuix.kmp.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getCornerRadiusBottom(): Int {
    return getCornerRadiusBottom(context = LocalContext.current)
}


// https://dev.mi.com/distribute/doc/details?pId=1631
@SuppressLint("DiscouragedApi")
fun getCornerRadiusBottom(context:Context): Int {
    var radius = 0
    val resourceId = context.resources.getIdentifier("rounded_corner_radius_top", "dimen", "android")
    if (resourceId > 0) radius = context.resources.getDimensionPixelSize(resourceId)
    return radius
}