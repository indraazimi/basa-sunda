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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.indraazimi.basasunda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val myAdapter: MainAdapter by lazy { MainAdapter() }

    private val viewModel: MainViewModel by lazy {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val factory = MainViewModelFactory(prefs)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.recyclerView) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuChangeUrl) {
            showDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_change_url, null)
        val editText = view.findViewById<EditText>(R.id.editText)
        editText.setText(viewModel.getUrl())

        AlertDialog.Builder(this)
            .setTitle(R.string.change_url)
            .setView(view)
            .setPositiveButton(R.string.simpan) { _, _ ->
                viewModel.saveUrl(editText.text.toString())
            }
            .setNegativeButton(R.string.batal) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}