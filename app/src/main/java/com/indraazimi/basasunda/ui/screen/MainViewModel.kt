/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indraazimi.basasunda.model.Category
import com.indraazimi.basasunda.network.ApiStatus
import com.indraazimi.basasunda.network.CategoryApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var categoryData = mutableStateOf(listOf<Category>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf("")
        private set

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                categoryData.value = CategoryApi.service.getCategory()
                status.value = ApiStatus.SUCCESS

            } catch (e: Exception) {
                status.value = ApiStatus.ERROR
                errorMessage.value = "Gagal mengambil data: ${e.message}"
            }
        }
    }
}