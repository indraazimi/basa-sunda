/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indraazimi.basasunda.network.BaseUrlRepository
import com.indraazimi.basasunda.ui.screen.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val urlRepository = BaseUrlRepository(context.applicationContext)
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(urlRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}