package com.example.sportsapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.ui.theme.StaticTextTypography

@Composable
fun UserInputDetailsTemplateScreen(title: String, indicatorPosition: Int, description: String, buttonClick: () -> Unit = {}, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text(modifier = Modifier.padding(start = 10.dp), text = title, style = StaticTextTypography.body1)
            PageIndicator(modifier = Modifier.padding(top = 30.dp), amount = 5, currentPosition = indicatorPosition)
            Text(modifier = Modifier.padding(top = 30.dp), text = description, fontSize = 20.sp, color = Color.White)
            content()
            Spacer(modifier = Modifier.weight(1f))
            TextBaseButton(modifier = Modifier.fillMaxWidth().height(LocalConfiguration.current.screenHeightDp.dp.div(20)), text = "NEXT", textColor = PrimaryColorNavy, backgroundColor = Color.White, onClick = { buttonClick() }) {
            }
        }
    }
}

@Composable
fun PageIndicator(modifier: Modifier = Modifier, amount: Int, currentPosition: Int) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        for (i in 0..amount) {
            Box(
                modifier = Modifier.height(4.dp).weight(1f).background(if (i < currentPosition) SecondaryColor else Color.White),
            )
        }
    }
}

@Preview
@Composable
fun PreviewTemplate() {
    UserInputDetailsTemplateScreen(title = "Goals", indicatorPosition = 1, description = "Let's start with your goals") {
    }
}
