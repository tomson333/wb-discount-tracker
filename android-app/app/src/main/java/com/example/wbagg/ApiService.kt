package com.example.wbagg
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/discounts")
    fun discounts(@Query("q") q: String? = null): Call<List<Product>>
}
