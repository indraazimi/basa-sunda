/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.navigation

import com.indraazimi.basasunda.model.Category

sealed class Screen (val route: String) {
    data object Main: Screen("mainScreen")
    data object Detail : Screen("detailScreen/{catId}/{label}/{color}") {
        fun withData(cat: Category) = "detailScreen/${cat.id}/${cat.label}/${cat.color}"
    }
}