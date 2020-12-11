package com.abdulrahman.nytimes.main.ui.main.viewModel

import com.abdulrahman.nytimes.main.data.model.ResultResponse
import com.abdulrahman.nytimes.main.ui.bases.BaseViewModel
import com.abdulrahman.nytimes.main.ui.bases.StateLiveData
import com.abdulrahman.nytimes.main.ui.main.data.PopularArticlesRepository
import com.abdulrahman.nytimes.main.utilities.NetworkConnection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PopularArticlesViewModel(
    private val popularArticlesRepository: PopularArticlesRepository,
    private val networkConnection: NetworkConnection,
    context: CoroutineDispatcher = Dispatchers.Main
) : BaseViewModel(mainContext = context) {

     val allArticlesLiveData: StateLiveData<ResultResponse> = StateLiveData()

    fun getAllArticles(): StateLiveData<ResultResponse> {

        if (!networkConnection.isConnected()) {
            publishNoInternet(allArticlesLiveData)
            return allArticlesLiveData
        }
        launchJob {
            performApiCall(allArticlesLiveData, popularArticlesRepository.getMostPopularArticles())
        }
        publishLoading(allArticlesLiveData)

        return allArticlesLiveData
    }
}
