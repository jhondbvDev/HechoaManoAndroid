package com.example.hechoamano.data.network

import com.example.hechoamano.data.authentication.BasicAuthInterceptor
import com.example.hechoamano.data.authentication.LoginRequest
import com.example.hechoamano.data.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

class LoginClient {

    private lateinit var apiService: ApiClient

    fun getLoginService(): ApiClient {

        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiClient::class.java)
        }

        return apiService
    }

}