/*
 * Copyright (c) 2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indraazimi.basasunda.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val prefs: SharedPreferences) : ViewModel() {

    companion object {
        private const val DEFAULT_URL = "https://d3ifcool.org/basasunda/v1/"
        private const val KEY_URL = "url"
    }

    private val data = MutableLiveData<List<Category>>()
    private val status = MutableLiveData<ApiStatus>()
    private var errorMessage : String? = null

    init {
        HewanApi.init(getUrl())
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(HewanApi.service.getCategories())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getUrl(): String {
        return prefs.getString(KEY_URL, DEFAULT_URL) ?: DEFAULT_URL
    }

    fun saveUrl(newUrl: String) {
        prefs.edit().putString(KEY_URL, newUrl).apply()
    }

    fun getData(): LiveData<List<Category>> = data

    fun getStatus(): LiveData<ApiStatus> = status

    fun getErrorMessage(): String = errorMessage ?: ""
}