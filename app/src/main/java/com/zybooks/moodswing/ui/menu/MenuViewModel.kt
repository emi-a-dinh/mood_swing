package com.zybooks.moodswing.ui.menu

import androidx.lifecycle.ViewModel
import com.zybooks.moodswing.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MenuViewModel : ViewModel(){
    data class MenuItem(val menu_type: String, val imageId: Int, val name: String, val description: String)


    private val _menuItems = MutableStateFlow(listOf(
        MenuItem("Drinks", R.drawable.question_mark, "Moo'd Swing Special $10",
            "Ask the chef, seasonal drink"),
        MenuItem("Drinks", R.drawable.shirley_temple, "Shirley Temple $10",
            "Ginger Ale, Cherry Grenadine, topped with a maraschino Cherry"),
        MenuItem("Drinks", R.drawable.margarita, "Margarita $10",
            "Tequila, lime juice, triple sec, and salt rim."),
        MenuItem("Drinks", R.drawable.mocha, "Mocha $7",
            "Espresso, chocolate syrup, steamed milk, and whipped cream."),
        MenuItem("Appetizers", R.drawable.fries, "Truffle Oil French Fries $20",
            "Hand-cut Fresh Kennebec Potatos, garlic and truffle infused olive oil"),
        MenuItem("Appetizers", R.drawable.scallops, "Seared Scallops $20",
            "Japanese Scallops with Cauliflower Puree and Curry Oil"),
        MenuItem("Appetizers", R.drawable.tartare, "Steak Tartare $20",
            "Vibrant, fresh flavors and a melt-in-your-mouth texture"),
        MenuItem("Main", R.drawable.wagyu, "A5 Wagyu Steak $ MSRP",
            "Hokkaido A5, Buttery, Delicate, Tender"),
        MenuItem("Main", R.drawable.tomahawk, "Tomahawk $ MSRP",
            "USDA Choice, Rosemary and Garlic infused Butter"),
        MenuItem("Main", R.drawable.chicken, "Chicken $ 35",
            "Juicy Chicken Breast, Creamy Potato Puree, Pine nuts")
    ))

    //ensure that the ui can't alter the menu items, only view it
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    private val _categories = MutableStateFlow(listOf("Drinks", "Appetizers", "Main"))
    val categories: StateFlow<List<String>> = _categories.asStateFlow()


    private val _selectedCategory = MutableStateFlow("Drinks")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }


}