package com.indraazimi.basasunda.network

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.indraazimi.basasunda.model.Category
import com.indraazimi.basasunda.model.Word
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

private val Context.dataStore by preferencesDataStore(name = "settings")

object BaseUrlRepository {
    private val BASE_URL_KEY = stringPreferencesKey("base_url")
    private const val DEFAULT_URL = "https://d3ifcool.org/basasunda/v1/"

    fun getBaseUrl(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[BASE_URL_KEY] ?: DEFAULT_URL
        }
    }

    suspend fun updateBaseUrl(context: Context, newUrl: String) {
        val correctedUrl = if (newUrl.endsWith("/")) newUrl else "$newUrl/"

        context.dataStore.edit { preferences ->
            preferences[BASE_URL_KEY] = correctedUrl
        }
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

enum class ApiStatus { LOADING, ERROR, SUCCESS }