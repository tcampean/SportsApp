package com.example.sportsapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsapp.ui.theme.PrimaryColor

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

@Composable
fun PairedCards(leftCard: @Composable () -> Unit = {}, rightCard: @Composable () -> Unit = {}) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        leftCard()
        rightCard()
    }
}

@Composable
fun ActionCard(modifier: Modifier = Modifier, label: String, color: Color = Color.White, borderColor: Color = Color.Transparent, borderWidth: Int = 2, textColor: Color = PrimaryColor, icon: Int, onClick: () -> Unit = {}) {
    val screenSize = LocalConfiguration.current.screenWidthDp.dp
    Card(
        modifier = modifier.clickable { onClick() }.height(screenSize / 2),
        shape = RoundedCornerShape(CornerSize(20.dp)),
        backgroundColor = color,
        border = BorderStroke(borderWidth.dp, borderColor),
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(modifier = Modifier.weight(3f), painter = painterResource(id = icon), contentDescription = label, contentScale = ContentScale.FillHeight)
                Text(modifier = Modifier.weight(1f), text = label, color = textColor)
            }
        }
    }
}

@Composable
fun ActionCardLong(modifier: Modifier = Modifier, label: String, color: Color = Color.White, borderColor: Color = Color.Transparent, borderWidth: Int = 2, textColor: Color = PrimaryColor, icon: Int, onClick: () -> Unit = {}) {
    val screenSize = LocalConfiguration.current.screenWidthDp.dp
    Card(
        modifier = modifier.clickable { onClick() }.height(screenSize / 2),
        shape = RoundedCornerShape(CornerSize(20.dp)),
        backgroundColor = color,
        border = BorderStroke(borderWidth.dp, borderColor),
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.TopStart) {
            Row(modifier = Modifier.fillMaxHeight()) {
                Image(modifier = Modifier.align(Alignment.CenterVertically).height(screenSize / 4).width(screenSize / 4), painter = painterResource(id = icon), contentDescription = label, contentScale = ContentScale.FillBounds)
                Text(modifier = Modifier, text = label, color = textColor, fontSize = 35.sp, textAlign = TextAlign.End, overflow = TextOverflow.Clip)
            }
        }
    }
}
