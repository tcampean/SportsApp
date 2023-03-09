package com.example.sportsapp.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextField(modifier: Modifier = Modifier, value: String, label: String, textStyle: TextStyle, onValueChange: (String) -> Unit = {}, textFieldColors: TextFieldColors, visualTransformation: VisualTransformation = VisualTransformation.None ) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        shape = RoundedCornerShape(CornerSize(10.dp)),
        textStyle = textStyle,
        colors = textFieldColors,
        onValueChange = { onValueChange(it) },
        visualTransformation = visualTransformation,
        label = { Text(label, fontFamily = FontFamily.Serif) },
    )
}
