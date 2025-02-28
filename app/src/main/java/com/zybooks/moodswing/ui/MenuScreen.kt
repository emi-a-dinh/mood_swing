package com.zybooks.moodswing.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.zybooks.moodswing.R


data class MenuItem(val menu_type: String, val imageId: Int, val name: String, val description: String)

@Composable
fun MenuScreen(viewModel: MenuViewModel) {
    val menuItems = listOf(
        MenuItem("Drinks", R.drawable.shirley_temple, "Shirley Temple $10",
            "Ginger Ale, Cherry Grenadine, topped with a maraschino Cherry"),
        MenuItem("Drinks", R.drawable.margarita, "Margarita $10",
            "Tequila, lime juice, triple sec, and salt rim."),
        MenuItem("Drinks", R.drawable.mocha, "Mocha $7",
            "Espresso, chocolate syrup, steamed milk, and whipped cream.")
    )


    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    item{
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color.Gray.copy(alpha = 0.3f), // Light gray for subtle effect
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Drinks",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 4.dp, start = 8.dp)
        )
    }
        items(menuItems) {
            item -> MenuCard(item.imageId, item_name = item.name, item_desc = item.description)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item{
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = Color.Gray.copy(alpha = 0.3f), // Light gray for subtle effect
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Appetizers",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp)
            )
        }

        items(menuItems) {
                item -> MenuCard(item.imageId, item_name = item.name, item_desc = item.description)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }


}

@Composable
fun MenuCard(imageId: Int, item_name: String, item_desc: String) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        border = BorderStroke(3.dp, Color.Black),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .size(width = 350.dp, height = 280.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = item_name)
                Text(
                    text = item_desc
                )

            }

        }

    }
}










