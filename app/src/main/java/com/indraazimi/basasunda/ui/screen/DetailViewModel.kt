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
import com.indraazimi.basasunda.model.Word
import com.indraazimi.basasunda.network.WordApi
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    var wordData = mutableStateOf(listOf<Word>())
        private set

    fun retrieveData(categoryId: Int) {
        viewModelScope.launch {
            try {
                wordData.value = WordApi.service.getWordByCategoryId(categoryId)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error retrieving data: ${e.message}")
            }
        }
    }
}