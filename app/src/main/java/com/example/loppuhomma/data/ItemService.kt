package com.example.loppuhomma.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemService {
    @GET("m=itemdb_oldschool/api/catalogue/detail.json")
    suspend fun getItemDetails(@Query("item") itemId: Int): Response<ItemResponse>
}