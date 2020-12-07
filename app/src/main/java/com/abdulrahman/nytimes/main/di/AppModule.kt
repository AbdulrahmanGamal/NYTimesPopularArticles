package com.abdulrahman.nytimes.main.di

import android.content.Context
import android.net.ConnectivityManager
import com.abdulrahman.nytimes.main.data.retrofit.ApiInterface
import com.abdulrahman.nytimes.main.utilities.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    val get = module {
        single { gsonFactory() }
        single { coroutineAdapterFactory() }
        single { getWebserviceInstance(get(), get()) }
        single {
            get<Retrofit>().create(ApiInterface::class.java)
        }
        single {
            get<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }

    private fun getWebserviceInstance(
        gsonFactory: GsonConverterFactory,
        coroutine: CoroutineCallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.api.URL)
        .addConverterFactory(gsonFactory)
        .addCallAdapterFactory(coroutine)
        .build()


    private fun gsonFactory() = GsonConverterFactory.create()
    private fun coroutineAdapterFactory() = CoroutineCallAdapterFactory.invoke()

}
