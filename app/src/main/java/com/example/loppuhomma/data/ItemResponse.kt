package com.example.loppuhomma.data

data class ItemResponse(
    val item: Item
)

data class Item(
    val name: String,
    val icon_large: String,
    val current: CurrentPrice,
    val description: String,
    val day30: PriceChange,
    val day90: PriceChange,
    val day180: PriceChange
)


data class CurrentPrice(
    val price: String
)

data class PriceChange(
    val change: String,
    val trend: String,
)