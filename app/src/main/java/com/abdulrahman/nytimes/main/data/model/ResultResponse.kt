package com.abdulrahman.nytimes.main.data.model


import com.abdulrahman.nytimes.main.ui.bases.BasicResponse
import com.google.gson.annotations.SerializedName


data class ResultResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("num_results")
    val numResults: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("status")
    val status: String
): BasicResponse()