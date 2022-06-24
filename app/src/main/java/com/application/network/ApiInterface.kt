package com.application.network

import com.application.model.DeliveryItem
import retrofit2.Call
import retrofit2.http.GET


val retroService: ApiInterface by lazy {
    retrofit.create(ApiInterface::class.java)
}

interface ApiInterface {

    @GET("deliveries")
    fun getDeliveries(): Call<DeliveryItem>
}