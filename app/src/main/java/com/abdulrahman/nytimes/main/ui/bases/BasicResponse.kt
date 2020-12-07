package com.abdulrahman.nytimes.main.ui.bases

import com.google.gson.annotations.SerializedName


open class BasicResponse(
    var message: String? = "",
    var fault: Fault? = null
)

data class Fault(
    @SerializedName("faultstring")
    val faultString: String
)

