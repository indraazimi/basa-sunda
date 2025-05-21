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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.indraazimi.basasunda.R
import com.indraazimi.basasunda.navigation.Screen
import com.indraazimi.basasunda.network.ApiStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel()
    var showDialog by remember { mutableStateOf(false) }
    var baseUrl by viewModel.baseUrl

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.ubah),
                        )
                    }
                }
            )
        }
    ) {
        innerPadding ->
        MainContent(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
        )

        if (showDialog) {
            BaseUrlDialog(
                onDismiss = { showDialog = false },
                onConfirm = { newBaseUrl ->
                    viewModel.updateBaseUrl(newBaseUrl)
                    showDialog = false
                },
                currentBaseUrl = baseUrl,
            )
        }
    }
}

@Composable
fun MainContent(modifier: Modifier, navController: NavController) {
    val viewModel: MainViewModel = viewModel()
    val data by viewModel.categoryData
    val errorMessage by viewModel.errorMessage
    val status by viewModel.status.collectAsState()

    when(status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        ApiStatus.SUCCESS -> {
            LazyColumn (
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data) { item ->
                    CategoryItem(
                        title = item.label,
                        onClick = { navController.navigate(Screen.Detail.masukCatId(item.id, item.label)) }
                    )
                    HorizontalDivider()
                }
            }
        }
        ApiStatus.ERROR -> {
            Column (
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {viewModel.retrieveData()}) {
                    Text(
                        text = stringResource(R.string.coba_lagi)
                    )
                }

            }
        }
    }


}

@Composable
fun CategoryItem(
    title: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface)
            .height(88.dp)
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

