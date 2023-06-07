/*
 * Copyright (c) 2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indraazimi.basasunda.databinding.ListWordBinding
import com.indraazimi.basasunda.model.Word

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    private val data = mutableListOf<Word>()

    fun updateData(newData: List<Word>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListWordBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        private val binding: ListWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) = with(binding) {
            sundaTextView.text = word.sunda
            defaultTextView.text = word.label
        }
    }
}