/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.indraazimi.basasunda.R
import com.indraazimi.basasunda.model.Word
import com.indraazimi.basasunda.network.ApiStatus
import com.indraazimi.basasunda.network.BasaSundaApi
import com.indraazimi.basasunda.ui.component.ErrorMessage
import com.indraazimi.basasunda.ui.component.LoadingIndicator
import com.indraazimi.basasunda.ui.component.getDensityQualifier
import com.indraazimi.basasunda.ui.component.toComposeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(catId: Int, label: String, navController: NavController) {
    val viewModel: DetailViewModel = viewModel()
    val data by viewModel.wordData

    LaunchedEffect(catId) {
        viewModel.retrieveData(catId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = label)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        DetailContent(
            modifier = Modifier.padding(innerPadding),
            words = data,
            catId = catId
        )
    }
}

@Composable
fun DetailContent(modifier: Modifier = Modifier, words: List<Word>, catId: Int) {
    val viewModel: DetailViewModel = viewModel()
    val status by viewModel.status.collectAsState()
    val errorMessage by viewModel.errorMessage
    val warnaBackground by viewModel.colorItem

    when (status) {
        ApiStatus.LOADING -> {
            LoadingIndicator(modifier)
        }

        ApiStatus.SUCCESS -> {
            LazyColumn(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(words) {
                    WordItem(it, warnaBackground)
                    HorizontalDivider()
                }
            }
        }

        ApiStatus.ERROR -> {
            ErrorMessage(errorMessage, modifier) {
                viewModel.retrieveData(catId)
            }
        }
    }
}

@Composable
fun WordItem(word: Word, warnaBackground: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(warnaBackground.toComposeColor())
            .height(88.dp)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (word.image.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BasaSundaApi.getImageUrl(word.image, getDensityQualifier()))
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.gambar, word.label),
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.broken_img),
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = word.label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = word.sunda,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }
    }
}