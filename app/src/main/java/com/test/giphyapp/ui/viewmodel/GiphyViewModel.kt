package com.test.giphyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.giphyapp.data.model.GiphyResponse
import com.test.giphyapp.domain.repository.GiphyRepository
import com.test.giphyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository) : ViewModel() {

    val giphsLiveData: MutableLiveData<Resource<GiphyResponse>> = MutableLiveData()
    var giphsResponse: GiphyResponse? = null

    init {

    }

    fun getGiphs() = viewModelScope.launch(Dispatchers.IO) {
        safeGetGiphssCall()
    }

    private suspend fun safeGetGiphssCall(){
        giphsLiveData.postValue(Resource.Loading())
        try{
            val response = giphyRepository.getGiphs()
            giphsLiveData.postValue(handleGiphyResponse(response))
        }
        catch (exception : Exception){
            when(exception){
                is IOException -> giphsLiveData.postValue(
                    Resource.Error(exception.message ?: "Network Failure"))
                else -> giphsLiveData.postValue(
                    Resource.Error(exception.message ?: "Conversion Error"))
            }
        }
    }

    private fun handleGiphyResponse(response: Response<GiphyResponse>): Resource<GiphyResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (giphsResponse == null)
                    giphsResponse = resultResponse
                else {
                    val oldGiphs = giphsResponse?.data
                    val newGiphs = resultResponse.data
                    oldGiphs?.addAll(newGiphs)
                }
                return Resource.Success(giphsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
