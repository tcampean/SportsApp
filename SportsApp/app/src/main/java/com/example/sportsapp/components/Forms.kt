package com.example.sportsapp.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SearchBarColor

@Composable
fun TextField(modifier: Modifier = Modifier, value: String, label: String = "", placeholder: String = "", textStyle: TextStyle, onValueChange: (String) -> Unit = {}, textFieldColors: TextFieldColors, keyboardType: KeyboardType = KeyboardType.Text, visualTransformation: VisualTransformation = VisualTransformation.None) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        shape = RoundedCornerShape(CornerSize(20.dp)),
        textStyle = textStyle,
        colors = textFieldColors,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        label = { if (label.isNotEmpty()) Text(label, fontFamily = FontFamily.Serif) },
        placeholder = { Text(placeholder, fontFamily = FontFamily.Serif) },
    )
}

@Composable
fun SearchBarTextField(modifier: Modifier = Modifier, value: String, label: String, textStyle: TextStyle, onValueChange: (String) -> Unit = {}, visualTransformation: VisualTransformation = VisualTransformation.None) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        shape = RoundedCornerShape(CornerSize(20.dp)),
        textStyle = textStyle,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = PrimaryColorNavy,
            backgroundColor = SearchBarColor,
            cursorColor = PrimaryColorNavy,
            placeholderColor = PrimaryColorNavy,
            focusedBorderColor = Color.Unspecified,
        ),
        onValueChange = { onValueChange(it) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
        placeholder = { Text(label, fontFamily = FontFamily.Serif) },
    )
}

@Composable
fun InputTextField(modifier: Modifier = Modifier, value: String, placeholder: String = "", textStyle: TextStyle = TextStyle.Default, onValueChange: (String) -> Unit = {}, textFieldColors: TextFieldColors, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), keyboardActions: KeyboardActions = KeyboardActions { }, visualTransformation: VisualTransformation = VisualTransformation.None) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        shape = RoundedCornerShape(CornerSize(20.dp)),
        textStyle = textStyle,
        colors = textFieldColors,
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        placeholder = { Text(placeholder, fontFamily = FontFamily.Serif) },
    )
}
