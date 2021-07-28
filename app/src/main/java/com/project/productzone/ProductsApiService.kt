package com.project.productzone

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://fakestoreapi.com/"

interface ProductsApiService {
    @GET("products")
    suspend fun getProducts(): ArrayList<ProductsItem>
}


object ProductService {
    val productInstance: ProductsApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productInstance = retrofit.create(ProductsApiService::class.java)
    }
}