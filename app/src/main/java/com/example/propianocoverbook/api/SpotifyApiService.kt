package com.example.propianocoverbook.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// Retrofit API Interface
interface SpotifyApiService {
    @GET("v1/search")
    suspend fun searchMusic(
        @Query("q") query: String,
        @Query("type") type: String = "track,artist",
        @Query("limit") limit: String = "",
        @Header("Authorization") authHeader: String
    ): Response<SpotifySearchResponse>
}
