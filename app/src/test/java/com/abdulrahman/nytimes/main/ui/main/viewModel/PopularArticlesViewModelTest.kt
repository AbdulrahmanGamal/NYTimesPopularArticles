package com.abdulrahman.nytimes.main.ui.main.viewModel

import com.abdulrahman.nytimes.main.data.model.Result
import com.abdulrahman.nytimes.main.data.model.ResultResponse
import com.abdulrahman.nytimes.main.ui.bases.StateData
import com.abdulrahman.nytimes.main.ui.bases.result.Single
import com.abdulrahman.nytimes.main.ui.main.data.PopularArticlesRepository
import com.abdulrahman.nytimes.main.utilities.NetworkConnection
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class PopularArticlesViewModelTest : BaseObservingViewModelTest() {
    private val mockedMainThread = newSingleThreadContext("UI thread")

    private val popularArticlesRepository: PopularArticlesRepository = mockk()
    private val networkConnection: NetworkConnection = mockk()
    private lateinit var viewModel: PopularArticlesViewModel

    private val expectedResult = Result(
        abstract = "The metal structure has been removed, Utah officials said on Saturday, adding that they had not taken it down.",
        adxKeywords = "test",
        assetId = 100000007477435,
        byline = "By Bryan Pietsch",
        desFacet = listOf("Art", "Sculpture"),
        etaId = 0,
        geoFacet = listOf("Utah"),
        id = 11,
        media = emptyList(),
        nytdsection = "test",
        orgFacet = listOf("Bureau of Land Management"),
        publishedDate = "12-12-2020",
        section = "U.S.",
        source = "New York Times",
        subsection = "",
        title = "That Mysterious Monolith in the Utah Desert? It’s Gone, Officials Say",
        type = "Article",
        updated = "nyt://article/6bb97b0e-475e-5888-a484-962f2c241b3a",
        uri = "https://www.nytimes.com/2020/11/28/us/monolith-utah-disappeared.html",
        url = "https://www.nytimes.com/2020/11/28/us/monolith-utah-disappeared.html",
    )
    private val expectedResultResponse = ResultResponse(
        copyright = "Copyright (c) 2020 The New York Times Company.  All Rights Reserved.",
        numResults = 20,
        results = listOf(expectedResult),
        status = "OK"
    )

    @Before
    fun startTest() {
        Dispatchers.setMain(mockedMainThread)

        coEvery { networkConnection.isConnected() } returns true
        viewModel = PopularArticlesViewModel(popularArticlesRepository, networkConnection)
        coEvery { popularArticlesRepository.getMostPopularArticles() } returns Single.Success(
            expectedResultResponse
        )
    }

    @Test
    fun `when  get popular articles first status loading `() = runBlocking {
        viewModel.getAllArticles().observeOnce { dateHolder ->
            Truth.assertThat(dateHolder.getLoading().getStatus())
                .isEqualTo(StateData.DataStatus.LOADING)
        }
    }

    @Test
    fun `when get popular articles return data successfully `() = runBlocking {
        viewModel.allArticlesLiveData.postSuccess(expectedResultResponse)
        Truth.assertThat(viewModel.allArticlesLiveData.getOrAwaitValue().getData()?.results?.size)
            .isEqualTo(1)
    }

    @Test
    fun `when get popular articles return data Throw exception`() = runBlocking {
        val expectedErrorResult = Throwable("error")
        viewModel.allArticlesLiveData.postError(expectedErrorResult)
        Truth.assertThat(viewModel.allArticlesLiveData.getOrAwaitValue().getError())
            .isEqualTo(expectedErrorResult)
    }

    @Test
    fun `when get popular articles and unexpected error happened`() = runBlocking {
        viewModel.allArticlesLiveData.postNotComplete()
        Truth.assertThat(
            viewModel.allArticlesLiveData.getOrAwaitValue().getNotCompleted().getStatus()
        ).isEqualTo(
            StateData.DataStatus.NOT_COMPLETED
        )
    }

    @Test
    fun `when no internet return no internet error`() = runBlocking {
        coEvery { networkConnection.isConnected() } returns false
        viewModel.getAllArticles().observeOnce { dateHolder ->
            Truth.assertThat(dateHolder.getNoInternet().getStatus())
                .isEqualTo(StateData.DataStatus.NO_INTERNET)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockedMainThread.close()
    }

}