package com.abdulrahman.nytimes.main.utilities

class Constants {
    object api {
        private val BASE_URL = "https://api.nytimes.com"
        private val API_VERSION = "/svc/mostpopular/v2/"
        val URL = BASE_URL + API_VERSION
        val API_KEY = "8GePz7zGxnp0c6ibvbH7oA7ZAGTKZBGM"
    }
}