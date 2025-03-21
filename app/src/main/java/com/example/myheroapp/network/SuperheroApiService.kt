package com.example.myheroapp.network

import com.example.myheroapp.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://superheroapi.com/api.php/"
private const val API_KEY = BuildConfig.SUPERHERO_API
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL + API_KEY + "/")
    .build()

interface SuperheroApiService {
    @GET("{id}")
    suspend fun getFullInfo(@Path("id") id: Int) : HeroInfo
}

object SuperheroApi {
    val retrofitService: SuperheroApiService by lazy {
        retrofit.create(SuperheroApiService::class.java)
    }
}

