package com.indraazimi.basasunda.network

import androidx.compose.runtime.mutableStateOf
import com.indraazimi.basasunda.model.Category
import com.indraazimi.basasunda.model.Word
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun createRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

object BaseUrlRepository {
    private val _baseUrl = MutableStateFlow("http://10.0.2.2/rest/")
    val baseUrl: StateFlow<String> = _baseUrl

    fun updateBaseUrl(newUrl: String) {
        val correctedUrl = if (newUrl.endsWith("/")) newUrl else "$newUrl/"
        _baseUrl.value = correctedUrl
    }
}

interface CategoryApiService {
    @GET("category.php")
    suspend fun getCategory(): List<Category>
}

interface WordApiService {
    @GET("word.php")
    suspend fun getWordByCategoryId(@Query("catid") categoryId: Int): List<Word>
}

enum class ApiStatus {
    LOADING,
    ERROR,
    SUCCESS
}
