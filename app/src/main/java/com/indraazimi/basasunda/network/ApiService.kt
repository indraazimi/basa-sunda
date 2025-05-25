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

interface BSApiService {
    @GET("category.php")
    suspend fun getCategory(): List<Category>

    @GET("word.php")
    suspend fun getWordByCategoryId(@Query("catid") categoryId: Int): List<Word>
}

object BasaSundaApi {
    private lateinit var baseUrl: String
    lateinit var service: BSApiService

    fun init(baseUrl: String) {
        this.baseUrl = baseUrl

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()

        service = retrofit.create(BSApiService::class.java)
    }
}

enum class ApiStatus { LOADING, ERROR, SUCCESS }