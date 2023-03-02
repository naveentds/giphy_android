package com.test.giphyapp.data.api

import com.test.giphyapp.data.model.GiphyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/trending")
    suspend fun getGiphs(
        @Query("api_key")
        apiKey: String
    ): Response<GiphyResponse>
}
