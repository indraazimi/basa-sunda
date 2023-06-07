/*
 * Copyright (c) 2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indraazimi.basasunda.databinding.ListCategoryBinding
import com.indraazimi.basasunda.model.Category

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val data = mutableListOf<Category>()

    fun updateData(newData: List<Category>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        private val binding: ListCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) = with(binding) {
            textView.text = category.label

            root.setOnClickListener {
                Log.d("ADAPTER", "Item clicked: ${category.id}")
            }
        }
    }
}