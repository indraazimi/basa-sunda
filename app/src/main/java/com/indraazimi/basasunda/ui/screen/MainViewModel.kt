/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indraazimi.basasunda.model.Category
import com.indraazimi.basasunda.network.CategoryApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var categoryData = mutableStateOf(listOf<Category>())
        private set

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch {
            try {
                categoryData.value = CategoryApi.service.getCategory()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error retrieving data: ${e.message}")
            }
        }
    }
}