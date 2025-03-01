package com.zybooks.moodswing.ui

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RewardsScreen(viewModel: RewardsViewModel) {
    var progress: Float by remember {mutableStateOf(.6f)}

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        RewardProgressCircle(progress)
    }

}

@Composable
fun RewardProgressCircle(progress : Float){
    val indicatorSize = 350.dp
    val trackwidth: Dp = (indicatorSize *.1f)
    val commonModifier = Modifier.size(indicatorSize)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(indicatorSize)
    ){
        CircularProgressIndicator(
            progress = { progress },
            modifier = commonModifier,
            strokeWidth = trackwidth
        )
        Column( horizontalAlignment = Alignment.CenterHorizontally){
            Text (
                text = "${(progress * 10).toInt()} / 10 ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = if (progress < .5f) "Keep going!" else "You've almost earned a reward"
            )
        }




    }


}