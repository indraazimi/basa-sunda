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
import com.indraazimi.basasunda.model.Word
import com.indraazimi.basasunda.network.ApiStatus
import com.indraazimi.basasunda.network.BasaSundaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    var wordData = mutableStateOf(listOf<Word>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf("")
        private set

    var colorItem = mutableStateOf("")
        private set

    fun retrieveData(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            colorItem.value = BasaSundaApi.service.getCategory()[categoryId-1].color
            try {
                wordData.value = BasaSundaApi.service.getWordByCategoryId(categoryId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                status.value = ApiStatus.ERROR
                errorMessage.value = "Gagal mengambil data: ${e.localizedMessage}"
            }
        }
    }
}