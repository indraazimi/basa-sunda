/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.ui.screen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indraazimi.basasunda.model.Category
import com.indraazimi.basasunda.network.ApiStatus
import com.indraazimi.basasunda.network.BaseUrlRepository
import com.indraazimi.basasunda.network.CategoryApiService
import com.indraazimi.basasunda.network.createRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainViewModel(context: Context) : ViewModel() {
    private lateinit var retrofit: Retrofit
    private lateinit var categoryApiService: CategoryApiService

    var categoryData = mutableStateOf(listOf<Category>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf("")
        private set

    var baseUrl = mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            BaseUrlRepository.getBaseUrl(context).collectLatest { newUrl ->
                baseUrl.value = newUrl
                retrofit = createRetrofit(newUrl)
                categoryApiService = retrofit.create(CategoryApiService::class.java)
                retrieveData()
            }
        }
    }

    fun updateBaseUrl(context: Context, newUrl: String) {
        viewModelScope.launch {
            BaseUrlRepository.updateBaseUrl(context, newUrl)
        }
    }

    fun retrieveData() {
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                categoryData.value = categoryApiService.getCategory()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                status.value = ApiStatus.ERROR
                errorMessage.value =
                    "Gagal mengambil data: ${e.localizedMessage ?: "Unknown error"}"
            }
        }
    }
}