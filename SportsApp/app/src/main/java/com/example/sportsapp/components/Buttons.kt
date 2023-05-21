package com.example.sportsapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun BaseButton(modifier: Modifier = Modifier, backgroundColor: Color, enabled: Boolean = true, onClick: () -> Unit, content: @Composable () -> Unit) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        ),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        enabled = enabled,
        onClick = { onClick() },
    ) {
        content()
    }
}

@Composable
fun ImageButton(modifier: Modifier = Modifier, image: Painter, enabled: Boolean = true, onClick: () -> Unit) {
    Image(
        modifier = modifier.clickable(interactionSource = MutableInteractionSource(), indication = null) {
            if(enabled) {
                onClick()
            }
        },
        painter = image,
        contentDescription = "")
}
