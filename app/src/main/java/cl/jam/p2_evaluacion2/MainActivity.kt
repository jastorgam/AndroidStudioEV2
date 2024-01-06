package cl.jam.p2_evaluacion2

import android.content.Context
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

    private val vm: ProductsVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("MainActivity", "inicio de app")

        try {
            vm.getProductsOnDisc(openFileInput(ProductsRepository.FILE_NAME))
            Log.v("MainActivity", "Leyendo desde archivo ${ProductsRepository.FILE_NAME}")
        } catch (ex: Exception) {
            Log.e("MainActivity", "No existe archivo de productos")
        }

        setContent {
            AppNavBar(vm)
        }
    }

    override fun onPause() {
        super.onPause()
        vm.saveProductsOnDisc(openFileOutput(ProductsRepository.FILE_NAME, MODE_PRIVATE))
        Log.v("MainActivity::onPause", "Guardando a disco")
    }

    override fun onStop() {
        super.onStop()
        vm.saveProductsOnDisc(openFileOutput(ProductsRepository.FILE_NAME, MODE_PRIVATE))
        Log.v("MainActivity::onStop", "Guardando a disco")
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.saveProductsOnDisc(openFileOutput(ProductsRepository.FILE_NAME, MODE_PRIVATE))
        Log.v("MainActivity::onDestroy", "Guardando a disco")
    }
}





