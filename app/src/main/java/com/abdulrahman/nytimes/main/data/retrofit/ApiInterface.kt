package com.abdulrahman.nytimes.main.data.retrofit

import com.abdulrahman.nytimes.main.data.model.ResultResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("viewed/7.json")
     fun getMostPopular(@Query("api-key") apiKey: String): Deferred<ResultResponse>
}