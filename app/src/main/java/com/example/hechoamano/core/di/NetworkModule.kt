package com.example.hechoamano.core.di

import android.content.Context
import com.example.hechoamano.R
import com.example.hechoamano.data.authentication.BasicAuthInterceptor
import com.example.hechoamano.data.network.ApiClient
import com.example.hechoamano.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateFactory
import javax.inject.Singleton
import java.security.KeyStore
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.SSLContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit{

        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}