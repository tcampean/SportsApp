package com.example.sportsapp.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsapp.ui.theme.SecondaryColor

@Composable
fun CircularProgressbar1(
    size: Dp = 260.dp,
    foregroundIndicatorColor: Color = SecondaryColor,
    shadowColor: Color = Color.LightGray,
    indicatorThickness: Dp = 24.dp,
    total: Float = 2000f,
    dataUsage: Float = 900f,
    remaining: Float = 1100f,
    animationDuration: Int = 1000,
) {
    println("Data usage " + dataUsage)
    var dataUsageRemember by remember {
        mutableStateOf(dataUsage)
    }

    var remainingDataRemember by remember {
        mutableStateOf(remaining)
    }

    val dataUsageAnimate = animateFloatAsState(
        targetValue = dataUsageRemember,
        animationSpec = tween(
            durationMillis = animationDuration,
        ),
    )

    dataUsageRemember = dataUsage
    remainingDataRemember = remaining

    Box(
        modifier = Modifier
            .size(size),
        contentAlignment = Alignment.Center,
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .size(size),
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(shadowColor, Color.White),
                    center = Offset(x = this.size.width / 2, y = this.size.height / 2),
                    radius = this.size.height / 2,
                ),
                radius = this.size.height / 2,
                center = Offset(x = this.size.width / 2, y = this.size.height / 2),
            )

            drawCircle(
                color = Color.White,
                radius = (size / 2 - indicatorThickness).toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2),
            )

            val sweepAngle = (dataUsageAnimate.value) * 360 / total

            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx(),
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx(),
                ),
            )
        }
    }

}

@Composable
fun DisplayText(
    animateNumber: Float,
    toGo: Float,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        println("GOT HERE" + animateNumber + " " + toGo)
        Text(
            text = animateNumber.toInt().toString(),
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = toGo.toInt().toString() + " to go",
            color = Color.Black,
        )
    }
}

@Composable
fun ButtonProgressbar(
    backgroundColor: Color = Color(0xFF35898f),
    onClickButton: () -> Unit,
) {
    Button(
        onClick = {
            onClickButton()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        ),
    ) {
        Text(
            text = "Animate with Random Value",
            color = Color.White,
            fontSize = 16.sp,
        )
    }
}
