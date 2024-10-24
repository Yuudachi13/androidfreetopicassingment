package com.example.loppuhomma


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.loppuhomma.data.ItemService
import com.example.loppuhomma.data.itemIdList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavHost()
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "randomItem") {
        composable("randomItem") { RandomItemScreen(navController) }
        composable("info") { InfoScreen(navController) }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RandomItemScreen(navController: NavController) {
    var itemName by remember { mutableStateOf("") }
    var itemImage by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var kolmekymmentDayPrice by remember { mutableStateOf("") }
    var kolmekymmentDaytrend by remember { mutableStateOf("") }
    var yheksankymmentDaytrend by remember { mutableStateOf("") }
    var satakaheksankymmentDaytrend by remember { mutableStateOf("") }
    var yheksankymmentDayPrice by remember { mutableStateOf("") }
    var satakaheksankymmentDayPrice by remember { mutableStateOf("") }

    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://services.runescape.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val itemService = remember { retrofit.create(ItemService::class.java) }

    fun fetchNewItem() {
        println("Button pressed")
        val randomItemId = itemIdList.random()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = itemService.getItemDetails(randomItemId)
                if (response.isSuccessful) {
                    response.body()?.let { item ->
                        itemName = item.item.name
                        itemImage = item.item.icon_large
                        itemPrice = item.item.current.price
                        itemDescription = item.item.description
                        kolmekymmentDayPrice = item.item.day30.change
                        yheksankymmentDayPrice = item.item.day90.change
                        satakaheksankymmentDayPrice = item.item.day180.change
                        kolmekymmentDaytrend = item.item.day30.trend
                        yheksankymmentDaytrend = item.item.day90.trend
                        satakaheksankymmentDaytrend = item.item.day180.trend

                    }
                } else {
                    println("Error fetching item: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Error: $e")
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchNewItem()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Random OSRS Item")
        Text(text = itemName)
        if (itemImage.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(itemImage),
                contentDescription = "Item Image",
                modifier = Modifier.size(120.dp)
            )
        } else {
            Text(text = "No photo found")
        }
        Text(text = "Price: $itemPrice gp")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = " $itemDescription ")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { fetchNewItem() }) {
            Text("Roll new item")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("info") }) {
            Text("Info")
        }
    }
}
