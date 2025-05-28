/*
 * Copyright (c) 2023-2025 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman Berbasis Web 1.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.basasunda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.indraazimi.basasunda.ui.screen.DetailScreen
import com.indraazimi.basasunda.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("catId") { type = NavType.IntType },
                navArgument("label") { type = NavType.StringType },
                navArgument("color") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getInt("catId")
            val label = backStackEntry.arguments?.getString("label")
            val color = backStackEntry.arguments?.getString("color")
            if (catId != null && label != null && color != null) {
                DetailScreen(catId, label, color, navController)
            }
        }
    }
}