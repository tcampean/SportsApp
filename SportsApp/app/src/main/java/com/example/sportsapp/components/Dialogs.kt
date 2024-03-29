package com.example.sportsapp.components

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sportsapp.ui.theme.PrimaryColorNavy

@Composable
fun CustomDialog(title: String, value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit, onSaveClicked: () -> Unit) {
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = PrimaryColorNavy,
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) },
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    InputTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = "Enter name",
                        value = txtField.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            txtField.value = it
                        },
                        textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = PrimaryColorNavy,
                            backgroundColor = Color.White,
                            cursorColor = PrimaryColorNavy,
                            placeholderColor = PrimaryColorNavy,
                            focusedBorderColor = PrimaryColorNavy,
                            unfocusedBorderColor = PrimaryColorNavy,
                        ),
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        BaseButton(
                            onClick = {
                                if (txtField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    return@BaseButton
                                }
                                setValue(txtField.value)
                                onSaveClicked()
                            },
                            backgroundColor = PrimaryColorNavy,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                        ) {
                            Text(text = "Save", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeightUpdateDialog(value: Int, setShowDialog: (Boolean) -> Unit, onPlusClicked: () -> Unit, onMinusClicked: () -> Unit, onSaveClicked: () -> Unit) {
    val resetFlag = remember { mutableStateOf(true) }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row {
                    Text(
                        text = "Update your weight",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = PrimaryColorNavy,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        tint = colorResource(R.color.darker_gray),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable { setShowDialog(false) },
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                onMinusClicked()
                                resetFlag.value = !resetFlag.value
                            },
                        painter = painterResource(id = com.example.sportsapp.R.drawable.baseline_remove_24),
                        contentDescription = "minus",
                    )

                    if (resetFlag.value) {
                        Text(
                            text = value.toString(),
                            style = TextStyle(
                                fontSize = 48.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = PrimaryColorNavy,
                        )
                    } else {
                        Text(
                            text = value.toString(),
                            style = TextStyle(
                                fontSize = 48.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = PrimaryColorNavy,
                        )
                    }

                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                onPlusClicked()
                                resetFlag.value = !resetFlag.value
                            },
                        painter = painterResource(id = com.example.sportsapp.R.drawable.baseline_add_24),
                        contentDescription = "add",
                    )
                }

                BaseButton(
                    onClick = {
                        setShowDialog(false)
                        onSaveClicked()
                    },
                    backgroundColor = PrimaryColorNavy,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Text(text = "Save", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DiaryEntryDialog(title: String, value: String, value2: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit, setValue2: (String) -> Unit, onSaveClicked: () -> Unit) {
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }
    val txtField2 = remember { mutableStateOf(value2) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = PrimaryColorNavy,
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) },
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    InputTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = "Enter label",
                        value = txtField.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            txtField.value = it
                        },
                        textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = PrimaryColorNavy,
                            backgroundColor = Color.White,
                            cursorColor = PrimaryColorNavy,
                            placeholderColor = PrimaryColorNavy,
                            focusedBorderColor = PrimaryColorNavy,
                            unfocusedBorderColor = PrimaryColorNavy,
                        ),
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    InputTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = "Enter calories",
                        value = txtField2.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            txtField2.value = it
                        },
                        textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = PrimaryColorNavy,
                            backgroundColor = Color.White,
                            cursorColor = PrimaryColorNavy,
                            placeholderColor = PrimaryColorNavy,
                            focusedBorderColor = PrimaryColorNavy,
                            unfocusedBorderColor = PrimaryColorNavy,
                        ),
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        BaseButton(
                            onClick = {
                                if (txtField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    return@BaseButton
                                }
                                setValue(txtField.value)
                                setValue2(txtField2.value)
                                onSaveClicked()
                            },
                            backgroundColor = PrimaryColorNavy,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                        ) {
                            Text(text = "Save", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun prev2() {
    CustomDialog(title = "Name your meal plan", value = "yes", setShowDialog = { true }, setValue = {}, onSaveClicked = {})
}

@Preview
@Composable
fun prev() {
    WeightUpdateDialog(86, setShowDialog = { true }, onPlusClicked = {}, onMinusClicked = {}, onSaveClicked = {})
}

@Preview
@Composable
fun prev3() {
    DiaryEntryDialog(title = "Add to diary", value = "yes", value2 = "1234", setShowDialog = { true }, setValue = {}, setValue2 = {}, onSaveClicked = {})
}
