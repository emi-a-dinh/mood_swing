package com.zybooks.moodswing.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zybooks.moodswing.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val homeScreenPhotos = listOf(R.drawable.steak1, R.drawable.steak2, R.drawable.steak3)
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        var nextIndex = 0;
        while (true) {
            delay(3000) // Auto-scroll every 3 seconds
            nextIndex = nextIndex + 1;

            if (nextIndex == homeScreenPhotos.size) {
                listState.scrollToItem(0) // Instantly reset to the first image
                nextIndex = 0;
            } else {
                listState.animateScrollToItem(nextIndex)
            }
        }
    }


    LazyRow(state = listState) {
        items(homeScreenPhotos) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
            )
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to MoodSwing!", style = MaterialTheme.typography.headlineMedium)
        Text("Swipe delicious moods!", style = MaterialTheme.typography.bodyLarge)

    }

}



