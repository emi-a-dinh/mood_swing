package com.zybooks.moodswing.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zybooks.moodswing.R

@Composable
fun AboutUsScreen(onBackClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "About Us",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Mood Swing Logo",
                modifier = Modifier.size(100.dp)
            )
        }

        // Content sections
        item { SectionTitle("Our Story") }
        item { SectionText("Founded in 2025, Moo'd Swing is where innovation meets tradition. Our flavors are thoughtfully crafted, blending influences from diverse regions to create a truly unique steak experience.") }

        item { SectionTitle("Founders") }
        item { SectionText("Brandon Eng (Los Angeles, CA) and Emi Dinh (Elk Grove, CA)") }

        item { SectionTitle("Our Mission") }
        item { SectionText("To deliver the finest steak experience imaginable. We've traveled across the globe, mastering techniques from renowned chefs and learning about unique flavor profiles from different cultures.") }

        item { SectionTitle("Location") }
        item { SectionText("Based in Los Angeles, CA, Moo'd Swing brings world-class steak to the heart of the city.") }

        item { SectionTitle("Our Values") }
        item { SectionText("Innovation, tradition, and community. We believe great food brings people together, and every meal should be a celebration of flavor and connection.") }

        // Back Button
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
    )
}

@Composable
fun SectionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
