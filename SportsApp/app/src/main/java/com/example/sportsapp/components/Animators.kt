package com.example.sportsapp.components

import androidx.compose.animation.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun LogoAnimation(visible: Boolean, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            with(density) { -80.dp.roundToPx() }
        } +
            fadeIn(
                initialAlpha = 0.3f,
            ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        content()
    }
}

@Composable
fun LoginFormAnimation(visible: Boolean, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            with(density) { 20.dp.roundToPx() }
        } +
            fadeIn(
                initialAlpha = 0f,
            ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        content()
    }
}
