package com.example.sportsapp.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
