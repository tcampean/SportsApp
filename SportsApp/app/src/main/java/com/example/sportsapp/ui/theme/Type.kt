package com.example.sportsapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val StaticTextTypography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        color = Color.White,
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        color = Color.White,
    )
)

val ItemTypography = Typography(
    body1 = TextStyle(
        color = PrimaryColorNavy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
    ),
    body2 = TextStyle(
        color = PrimaryColorNavy,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    )
)

val LoginFormTypography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = PrimaryColorNavy,
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.White,
    )

)

