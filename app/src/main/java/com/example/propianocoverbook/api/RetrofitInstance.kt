package com.example.propianocoverbook.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.spotify.com/"

    // Retrofit のインスタンスを lazy 初期化で生成
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // SpotifyApiService の実装を取得
    val api: SpotifyApiService by lazy {
        retrofit.create(SpotifyApiService::class.java)
    }
}
