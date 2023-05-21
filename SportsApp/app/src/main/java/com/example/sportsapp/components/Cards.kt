package com.example.sportsapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseCard(modifier: Modifier, backgroundColor: Color = Color.Unspecified, strokeColor: Color = Color.Unspecified, cornerSize: Int = 20, elevation: Int = 0, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(cornerSize.dp)),
        backgroundColor = backgroundColor,
        border = BorderStroke(2.dp, strokeColor),
        elevation = elevation.dp,
    ) {
        content()
    }
}

@Composable
fun RoundBottomCard(modifier: Modifier, backgroundColor: Color = Color.Unspecified, strokeColor: Color = Color.Unspecified, cornerSize: Int = 20, elevation: Int = 0, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = cornerSize.dp, bottomEnd = cornerSize.dp),
        backgroundColor = backgroundColor,
        border = BorderStroke(2.dp, strokeColor),
        elevation = elevation.dp,
    ) {
        content()
    }
}

@Composable
fun RoundTopCard(modifier: Modifier, backgroundColor: Color = Color.Unspecified, strokeColor: Color = Color.Unspecified, cornerSize: Int = 20, elevation: Int = 0, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = cornerSize.dp, topEnd = cornerSize.dp),
        backgroundColor = backgroundColor,
        border = BorderStroke(2.dp, strokeColor),
        elevation = elevation.dp,
    ) {
        content()
    }
}
