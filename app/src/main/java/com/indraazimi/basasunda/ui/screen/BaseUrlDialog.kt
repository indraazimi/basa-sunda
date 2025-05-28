/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.indraazimi.basasunda.R
import com.indraazimi.basasunda.ui.theme.Brown900

@Composable
fun BaseUrlDialog(
    currentBaseUrl: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(currentBaseUrl) }

    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.ubah)) },
        text = {
            Column {
                Text(text = stringResource(R.string.masukkan))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Brown900
                ),
                onClick = { onConfirm(textFieldValue) },
                enabled = textFieldValue.isNotBlank()
            ) {
                Text(text = stringResource(R.string.simpan))
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Brown900
                ),
                onClick = onDismiss
            ) {
                Text(text = stringResource(R.string.batal))
            }
        }
    )
}