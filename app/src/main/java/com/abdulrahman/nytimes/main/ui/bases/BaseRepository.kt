package com.abdulrahman.nytimes.main.ui.bases

import com.abdulrahman.nytimes.main.data.retrofit.ApiInterface
import com.abdulrahman.nytimes.main.ui.bases.result.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseRepository(protected val apiInterface: ApiInterface) {
    protected suspend fun <RESULT : BasicResponse> requestToSingle(
        method: Deferred<RESULT>
    ): Single<RESULT> = withContext(Dispatchers.IO) {
        try {
            val result = method.await()
            when {
                result.fault != null -> {
                    Single.Error(Exception(result.fault?.faultString))
                }
                else -> {
                    Single.Success(result)
                }
            }
        } catch (exception: Exception) {
            Single.Error(exception)
        }
    }
}