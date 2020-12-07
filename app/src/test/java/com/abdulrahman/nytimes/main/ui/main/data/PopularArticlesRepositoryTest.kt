package com.abdulrahman.nytimes.main.ui.main.data

import com.abdulrahman.nytimes.main.data.model.Result
import com.abdulrahman.nytimes.main.data.model.ResultResponse
import com.abdulrahman.nytimes.main.data.retrofit.ApiInterface
import com.abdulrahman.nytimes.main.ui.bases.BasicResponse
import com.abdulrahman.nytimes.main.ui.bases.Fault
import com.abdulrahman.nytimes.main.ui.bases.result.Single
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PopularArticlesRepositoryTest {
    private val apiInterface: ApiInterface = mockk()

    private val repository = PopularArticlesRepository(apiInterface = apiInterface)

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

    @Test
    fun `on get popular api return articles`() = runBlocking {
        coEvery { apiInterface.getMostPopular(any()).await() } returns expectedResultResponse
        val actualResult = repository.getMostPopularArticles() as Single.Success
        Truth.assertThat(actualResult.data.copyright).isEqualTo(expectedResultResponse.copyright)
        Truth.assertThat(actualResult.data.results[0].id).isEqualTo(expectedResult.id)
    }

    @Test
    fun `on get popular api return empty list`() = runBlocking {
        val expectedResponse = ResultResponse(
            copyright = "Copyright (c) 2020 The New York Times Company.  All Rights Reserved.",
            numResults = 20,
            results = listOf(),
            status = "OK"
        )
        coEvery { apiInterface.getMostPopular(any()).await() } returns expectedResponse
        val actualResult = repository.getMostPopularArticles() as Single.Success
        Truth.assertThat(actualResult.data.results.size).isEqualTo(0)
    }

    @Test
    fun `on get popular api return Error`() = runBlocking {
        val errorMessage = "Invalid Token"
        coEvery {
            apiInterface.getMostPopular(any()).await() as BasicResponse
        } returns BasicResponse(fault = Fault(errorMessage))
        val actualResult = repository.getMostPopularArticles() as Single.Error
        Truth.assertThat(actualResult.exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `on get popular api return Exception`() = runBlocking {
        val expectedException = Exception("Error")
        coEvery {
            apiInterface.getMostPopular(any()).await()
        } throws expectedException
        val actualResult = repository.getMostPopularArticles() as Single.Error
        Truth.assertThat(actualResult.exception.message).isEqualTo(expectedException.message)
    }


}