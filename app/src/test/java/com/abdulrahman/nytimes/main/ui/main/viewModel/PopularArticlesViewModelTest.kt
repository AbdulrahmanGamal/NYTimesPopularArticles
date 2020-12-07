package com.abdulrahman.nytimes.main.ui.main.viewModel

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abdulrahman.nytimes.main.data.model.Result
import com.abdulrahman.nytimes.main.data.model.ResultResponse
import com.abdulrahman.nytimes.main.ui.bases.StateData
import com.abdulrahman.nytimes.main.ui.bases.result.Single
import com.abdulrahman.nytimes.main.ui.main.data.PopularArticlesRepository
import com.abdulrahman.nytimes.main.utilities.NetworkConnection
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PopularArticlesViewModelTest : BaseObservingViewModelTest() {

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
    override fun startTest() {
        loadKoinModules(module { single(override = true) { networkConnection } })
        coEvery { networkConnection.isConnected() } returns true
        viewModel = PopularArticlesViewModel(popularArticlesRepository, networkConnection)
        coEvery { popularArticlesRepository.getMostPopularArticles() } returns Single.Success(
            expectedResultResponse
        )
    }

    @Test
    fun `get popular articles first status loading `() = runBlocking {
        viewModel.getAllArticles().observeOnce { dateHolder ->
            Truth.assertThat(dateHolder.getLoading().getStatus())
                .isEqualTo(StateData.DataStatus.LOADING)
        }

    }

}