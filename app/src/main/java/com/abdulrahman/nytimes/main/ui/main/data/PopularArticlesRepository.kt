package com.abdulrahman.nytimes.main.ui.main.data

import com.abdulrahman.nytimes.main.ui.bases.result.Single
import com.abdulrahman.nytimes.main.data.model.ResultResponse
import com.abdulrahman.nytimes.main.data.retrofit.ApiInterface
import com.abdulrahman.nytimes.main.ui.bases.BaseRepository
import com.abdulrahman.nytimes.main.utilities.Constants

    class PopularArticlesRepository(apiInterface: ApiInterface) : BaseRepository(apiInterface) {
    suspend fun getMostPopularArticles(): Single<ResultResponse> {
        return requestToSingle(method = apiInterface.getMostPopular(Constants.api.API_KEY))
    }
}