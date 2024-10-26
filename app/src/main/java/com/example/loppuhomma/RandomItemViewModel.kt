package com.example.loppuhomma

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loppuhomma.data.ItemService
import com.example.loppuhomma.data.itemIdList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomItemViewModel : ViewModel() {
    var itemName = mutableStateOf("")
    var itemImage = mutableStateOf("")
    var itemPrice = mutableStateOf("")
    var itemDescription = mutableStateOf("")
    var kolmekymmentDayPrice = mutableStateOf("")
    var yheksankymmentDayPrice = mutableStateOf("")
    var satakaheksankymmentDayPrice = mutableStateOf("")
    var kolmekymmentDaytrend = mutableStateOf("")
    var yheksankymmentDaytrend = mutableStateOf("")
    var satakaheksankymmentDaytrend = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://services.runescape.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itemService = retrofit.create(ItemService::class.java)

    fun fetchNewItem() {
        isLoading.value = true
        val randomItemId = itemIdList.random()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = itemService.getItemDetails(randomItemId)
                if (response.isSuccessful) {
                    response.body()?.let { item ->
                        itemName.value = item.item.name
                        itemImage.value = item.item.icon_large
                        itemPrice.value = item.item.current.price
                        itemDescription.value = item.item.description
                        kolmekymmentDayPrice.value = item.item.day30.change
                        yheksankymmentDayPrice.value = item.item.day90.change
                        satakaheksankymmentDayPrice.value = item.item.day180.change
                        kolmekymmentDaytrend.value = item.item.day30.trend
                        yheksankymmentDaytrend.value = item.item.day90.trend
                        satakaheksankymmentDaytrend.value = item.item.day180.trend //nämä on nyt täällä vähän turhaan ku en jaksanu alkaa värkkää lisää
                    }
                } else {
                    println("Error fetching item: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Error: $e")
            } finally {
                isLoading.value = false
            }
        }
    }
}