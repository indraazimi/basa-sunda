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
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indraazimi.basasunda.model.Word
import com.indraazimi.basasunda.network.BaseUrlRepository
import com.indraazimi.basasunda.network.WordApiService
import com.indraazimi.basasunda.network.createRetrofit
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class DetailViewModel(context: Context) : ViewModel() {
    private lateinit var retrofit: Retrofit
    private lateinit var wordApiService: WordApiService

    var wordData = mutableStateOf(listOf<Word>())
        private set

    init {
        viewModelScope.launch {
            BaseUrlRepository.getBaseUrl(context).collectLatest { newUrl ->
                retrofit = createRetrofit(newUrl)
                wordApiService = retrofit.create(WordApiService::class.java)
            }
        }
    }

    fun retrieveData(categoryId: Int) {
        viewModelScope.launch {
            try {
                wordData.value = wordApiService.getWordByCategoryId(categoryId)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error retrieving data: ${e.message}")
            }
        }
    }
}