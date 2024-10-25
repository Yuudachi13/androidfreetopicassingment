package com.example.loppuhomma


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RandomItemScreen(navController: NavController, viewModel: RandomItemViewModel) {
    val itemName = viewModel.itemName.value
    val itemImage = viewModel.itemImage.value
    val itemPrice = viewModel.itemPrice.value
    val itemDescription = viewModel.itemDescription.value
    val isLoading = viewModel.isLoading.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Random OSRS Item")
        if (isLoading) {
            CircularProgressIndicator()
        } else {
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
            Text(text = itemDescription)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.fetchNewItem() }) {
            Text("Roll new item")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("info") }) {
            Text("Info")
        }
    }
}