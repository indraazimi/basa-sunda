package com.indraazimi.basasunda.ui.screen

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

class DetailViewModel : ViewModel() {
    private var retrofit = createRetrofit(BaseUrlRepository.baseUrl.value)
    private var wordApiService = retrofit.create(WordApiService::class.java)
    var wordData = mutableStateOf(listOf<Word>())
        private set
    init {
        viewModelScope.launch {
            BaseUrlRepository.baseUrl.collectLatest { newUrl ->
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
