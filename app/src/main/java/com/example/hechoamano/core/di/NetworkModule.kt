package com.example.hechoamano.core.di

import android.content.Context
import com.example.hechoamano.R
import com.example.hechoamano.data.authentication.BasicAuthInterceptor
import com.example.hechoamano.data.network.ApiClient
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
        // Carga el certificado desde los recursos raw
        val certificate = CertificateFactory.getInstance("X.509")
            .generateCertificate(context.resources.openRawResource(R.raw.mockable))

        // Crea un KeyStore conteniendo nuestro certificado de confianza
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("my_cert", certificate)
        }

        // Crea un TrustManager que confíe en el KeyStore
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(keyStore)
        }

        // Crea un SSLContext que utiliza nuestro TrustManager
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustManagerFactory.trustManagers, null)
        }

        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory)
            .addInterceptor(BasicAuthInterceptor("admin", "admin"))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://demo8127198.mockable.io/")
            //.baseUrl("https://hechoamanoapi.azurewebsites.net/")
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