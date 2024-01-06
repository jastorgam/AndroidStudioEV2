package cl.jam.p2_evaluacion2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import cl.jam.p2_evaluacion2.data.ProductsRepository
import cl.jam.p2_evaluacion2.ui.composables.AppNavBar
import cl.jam.p2_evaluacion2.ui.viewmodels.ProductsVM


class MainActivity : ComponentActivity() {

    /* Tuve problemas con el viewModel , no hacia bien la persistencia en disco, siendo que habian datos.
    Ahi me di cuenta que se estaban creando varias instancias del VM una en cada pagina (ya que solo
    estoy usando un vm para toda la app).

    SOLUCION: Tuve que pasar el viewmodel creado en MainActivity (que es donde se cargan los datos desde disco)
    al AppNavBar y desde ahi lo paso al HomePageUI  y al SettingsPageUI
    por eso el AppNavBar lleva el ViewModel productsViewModel
     */
    private val productsViewModel: ProductsVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.v("MainActivity", "inicio de app")

        try {
            productsViewModel.getProductsOnDisc(openFileInput(ProductsRepository.FILE_NAME))
            Log.v("MainActivity", "Leyendo desde archivo ${ProductsRepository.FILE_NAME}")
        } catch (ex: Exception) {
            Log.e("MainActivity", "No existe archivo de productos")
        }

        setContent {
            AppNavBar(productsViewModel)
        }
    }

    override fun onPause() {
        super.onPause()
        productsViewModel.saveProductsOnDisc(
            openFileOutput(
                ProductsRepository.FILE_NAME,
                MODE_PRIVATE
            )
        )
        Log.v("MainActivity::onPause", "Guardando a disco")
    }

    override fun onStop() {
        super.onStop()
        productsViewModel.saveProductsOnDisc(
            openFileOutput(
                ProductsRepository.FILE_NAME,
                MODE_PRIVATE
            )
        )
        Log.v("MainActivity::onStop", "Guardando a disco")
    }

    override fun onDestroy() {
        super.onDestroy()
        productsViewModel.saveProductsOnDisc(
            openFileOutput(
                ProductsRepository.FILE_NAME,
                MODE_PRIVATE
            )
        )
        Log.v("MainActivity::onDestroy", "Guardando a disco")
    }
}





