package com.zybooks.moodswing.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MenuScreen(viewModel: MenuViewModel) {
    val menuItems by viewModel.menuItems.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val filteredMenuItems = menuItems.filter { it.menu_type == selectedCategory }


    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{CategorySelector(viewModel)}
    item{
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color.Gray.copy(alpha = 0.3f), // Light gray for subtle effect
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = selectedCategory,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 4.dp, start = 8.dp)
        )
    }
        items(filteredMenuItems) {
            item -> MenuCard(item.imageId, item_name = item.name, item_desc = item.description)
            Spacer(modifier = Modifier.height(16.dp))
        }


    }


}

@Composable
fun CategorySelector(viewModel: MenuViewModel) {
    val categories by viewModel.categories.collectAsState()  // Ensure it's not null
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categories) { category ->
            OutlinedButton(onClick =  { viewModel.selectCategory(category) },
                border = if (category == selectedCategory) BorderStroke(2.dp, Color(0xFFFFD700)) else BorderStroke(0.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor= Color.White
            )) {
                Text(text = category)
            }
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










