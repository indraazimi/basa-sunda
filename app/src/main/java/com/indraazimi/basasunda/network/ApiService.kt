/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.network

import com.indraazimi.basasunda.model.Category
import com.indraazimi.basasunda.model.Word
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://10.0.2.2/rest/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CategoryApiService {
    @GET("category.php")
    suspend fun getCategory(): List<Category>
}

object CategoryApi {
    val service: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }
}

interface WordApiService {
    @GET("word.php")
    suspend fun getWordByCategoryId(@Query("catid") categoryId: Int): List<Word>
}

object WordApi {
    val service: WordApiService by lazy {
        retrofit.create(WordApiService::class.java)
    }
}

enum class ApiStatus {
    LOADING,
    ERROR,
    SUCCESS
}

