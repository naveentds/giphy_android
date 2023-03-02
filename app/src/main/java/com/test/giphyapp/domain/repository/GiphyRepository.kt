package com.test.giphyapp.domain.repository

import com.test.giphyapp.data.api.GiphyApi
import com.test.giphyapp.data.model.GiphyResponse
import com.test.giphyapp.util.Constants
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository @Inject constructor(
    private val giphyApi: GiphyApi
) {

    suspend fun getGiphs(): Response<GiphyResponse> {
        return giphyApi.getGiphs(apiKey = Constants.API_KEY)
    }
}