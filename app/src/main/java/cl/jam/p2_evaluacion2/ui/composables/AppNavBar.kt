package cl.jam.p2_evaluacion2.ui.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.jam.p2_evaluacion2.ui.viewmodels.ProductsVM

@Composable
fun AppNavBar(
    productsViewModel: ProductsVM,
    navController: NavHostController = rememberNavController()
) {
    /* Aca le paso el vm a las diferentes paginas para que no se pierdan los valores
    almacenados al inicio de la app.
     */

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomePageUI(
                productsViewModel,
                onButtonSettingsClicked = { navController.navigate("settings") }
            )

        }
        composable("settings") {
            SettingsPageUI(
                productsViewModel,
                onBackButtonClicked = { navController.navigate("home") }
            )
        }
    }
}