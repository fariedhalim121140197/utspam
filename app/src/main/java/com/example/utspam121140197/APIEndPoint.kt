package com.example.utspam121140197

import retrofit2.Call
import retrofit2.http.GET

interface APIEndPoint {
    @GET("users")
    fun getData(): Call<DataModel>
}