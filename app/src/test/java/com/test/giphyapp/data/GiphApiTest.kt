package com.test.giphyapp.data

import com.test.giphyapp.data.api.GiphyApi
import com.test.giphyapp.data.model.Giph
import com.test.giphyapp.data.model.GiphyResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class GiphApiTest {

    @Test
    fun testGetGiphsApi(){
        val giphyApi =  mockk<GiphyApi>()
        var mockResponse = mockk< Response<GiphyResponse>>(relaxUnitFun = true)
        coEvery { giphyApi.getGiphs("test_api_key") } returns mockResponse
        val mockGiphResponse = mockk<GiphyResponse>(relaxUnitFun = true)
        every { mockResponse.body() } returns mockGiphResponse
        val giphs = mutableListOf<Giph>()
        val giph = mockk<Giph>(relaxUnitFun = true)
        giphs.add(giph)
        every {mockGiphResponse.data} returns giphs
        every { giph.title } returns "test giph title"

        runBlockingTest { giphyApi.getGiphs("test_api_key") }
        Assert.assertTrue(mockResponse.body()!!.data[0].title == "test giph title")

    }

}