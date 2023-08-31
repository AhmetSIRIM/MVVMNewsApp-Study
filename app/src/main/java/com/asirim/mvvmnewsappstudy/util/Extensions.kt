package com.asirim.mvvmnewsappstudy.util

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.formatStringTime(): String {

    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy - HH:mm")

    return if (this == "null") "Unknown Time" else formatter.format(
        parser.parse(this) ?: "Unknown Time"
    )

}

/**
 * The following extension is described in this
 * article: [Navigation Components: A fix for “Navigation action cannot be found in the current destination” crash.](https://nezspencer.medium.com/navigation-components-a-fix-for-navigation-action-cannot-be-found-in-the-current-destination-95b63e16152e)
 */
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

/**
 * The following extension is described in this
 * article: [Navigation Components: A fix for “Navigation action cannot be found in the current destination” crash.](https://nezspencer.medium.com/navigation-components-a-fix-for-navigation-action-cannot-be-found-in-the-current-destination-95b63e16152e)
 */
@Suppress("unused")
fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int,
    @IdRes id: Int,
    args: Bundle? = null
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(id, args)
    }
}