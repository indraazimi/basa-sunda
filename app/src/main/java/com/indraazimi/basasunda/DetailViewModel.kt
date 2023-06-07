/*
 * Copyright (c) 2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda

import androidx.lifecycle.*
import com.indraazimi.basasunda.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val categoryId: Int) : ViewModel() {

    private val data = MutableLiveData<List<Word>>()
    private val status = MutableLiveData<ApiStatus>()
    private var errorMessage : String? = null

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(HewanApi.service.getWords(categoryId))
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<Word>> = data

    fun getStatus(): LiveData<ApiStatus> = status

    fun getErrorMessage(): String = errorMessage ?: ""
}