package com.example.utspam121140197

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {
    private const val BASE_URL: String = "https://reqres.in/api/"
    val endpoint: APIEndPoint
    get() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(APIEndPoint::class.java)
    }
}