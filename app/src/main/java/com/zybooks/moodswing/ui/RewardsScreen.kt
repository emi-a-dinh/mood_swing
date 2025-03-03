package com.zybooks.moodswing.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.moodswing.R


@Composable
fun RewardsScreen(viewModel: RewardsViewModel) {
    val progress by viewModel.progress.collectAsState()
    val rewardsCount by viewModel.rewardsCount.collectAsState()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        RewardProgressCircle(progress)
        Spacer(modifier = Modifier.padding(top = 32.dp))
        RewardSteakCard(progress)

        // Add buttons to test functionality
        Button(
            onClick = { viewModel.addReward() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add Punch")
        }

        Button(
            onClick = { viewModel.resetRewards() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Reset Rewards")
        }
    }
}

@Composable
fun RewardSteakCard(progress: Float){
    val totalSteaks = 10
    val filledSteaks = (progress * totalSteaks).toInt()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Reward Card",
            fontSize = 32.sp
        )
        Text(
            text = "Spend a minimum of $50 and earn a punch"
        )
        LazyVerticalGrid( columns = GridCells.Fixed(5), // 5 per row
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)) {

            items(totalSteaks) { index ->
                val imageRes = if (index < filledSteaks) R.drawable.filled_steak else R.drawable.empty_steak
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Steak Reward",
                    modifier = Modifier.size(50.dp)
                )
            }

        }
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