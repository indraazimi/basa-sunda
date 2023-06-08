/*
 * Copyright (c) 2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.indraazimi.basasunda.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_CATEGORY_ID = "catid"
        const val KEY_CATEGORY = "label"
        const val KEY_BACKGROUND = "background"
    }

    private val myAdapter: DetailAdapter by lazy {
        val backgroundColor = intent.getIntExtra(KEY_BACKGROUND, 0)
        DetailAdapter(backgroundColor)
    }

    private val viewModel: DetailViewModel by lazy {
        val categoryId = intent.getIntExtra(KEY_CATEGORY_ID, 0)
        val factory = DetailViewModelFactory(categoryId)
        ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra(KEY_CATEGORY)

        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }

        viewModel.getData().observe(this) { myAdapter.updateData(it) }
        viewModel.getStatus().observe(this) { updateProgress(it) }
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.text = viewModel.getErrorMessage()
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}