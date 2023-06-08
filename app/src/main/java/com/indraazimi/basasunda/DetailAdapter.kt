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
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.indraazimi.basasunda.databinding.ListWordBinding
import com.indraazimi.basasunda.model.Word

class DetailAdapter(private val color: Int) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

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

    inner class ViewHolder(
        private val binding: ListWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) = with(binding) {
            sundaTextView.text = word.sunda
            sundaTextView.setBackgroundColor(color)
            defaultTextView.text = word.label
            defaultTextView.setBackgroundColor(color)

            if (word.image.isEmpty()) {
                imageView.visibility = View.GONE
                return@with
            }

            Glide.with(imageView.context)
                .load(HewanApi.getImageUrl(word.image))
                .error(R.drawable.baseline_broken_image_24)
                .into(imageView)
        }
    }
}