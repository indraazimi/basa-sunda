package com.indraazimi.basasunda.network

import com.indraazimi.basasunda.model.Category
import com.indraazimi.basasunda.model.Word
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun createRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

interface CategoryApiService {
    @GET("category.php")
    suspend fun getCategory(): List<Category>
}

interface WordApiService {
    @GET("word.php")
    suspend fun getWordByCategoryId(@Query("catid") categoryId: Int): List<Word>
}

enum class ApiStatus { LOADING, ERROR, SUCCESS }