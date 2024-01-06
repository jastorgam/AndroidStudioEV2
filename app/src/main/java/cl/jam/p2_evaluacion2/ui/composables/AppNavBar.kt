package cl.jam.p2_evaluacion2.ui.composables

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.jam.p2_evaluacion2.ui.viewmodels.ProductsVM

@Composable
fun AppNavBar(
    vm: ProductsVM,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomePageUI(
                vm,
                onButtonSettingsClicked = { navController.navigate("settings") }
            )

        }
        composable("settings") {
            SettingsPageUI(
                vm,
                onBackButtonClicked = { navController.navigate("home") }
            )
        }
    }
}