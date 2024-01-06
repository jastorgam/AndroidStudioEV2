package cl.jam.p2_evaluacion2.ui.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.jam.p2_evaluacion2.data.ProductsRepository
import cl.jam.p2_evaluacion2.data.models.Product
import cl.jam.p2_evaluacion2.data.models.ProductsUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream


class ProductsVM() : ViewModel() {

    private val _productsRepository: ProductsRepository = ProductsRepository()
    val uiState: StateFlow<ProductsUIState> = _productsRepository.product

    private var orderAlpha = mutableStateOf(false)
    private var orderFirst = mutableStateOf(false)

    private lateinit var sharedPreferences: SharedPreferences

    private var job: Job? = null

    init {
        getProducts()
    }

    /***
     * Inicializo el sharedPreferences dentro del primer composable cargado
     * para poder tener acceso a las preferencias. Ya que el SharedPreferences solo se puede
     * pasar desde un composable y no desde el viewModel.
     */
    fun initSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
        getOrderAlpha()
        getOrderFirst()
    }

    fun getOrderAlpha(): Boolean {
        orderAlpha.value = sharedPreferences.getBoolean("order_alpha", false)
        return orderAlpha.value
    }

    fun setOderAlpha(order: Boolean) {
        job?.cancel()
        job = viewModelScope.launch {
            sharedPreferences.edit().putBoolean("order_alpha", order).apply()
            orderAlpha.value = order;
            getProducts()
        }
    }

    fun getOrderFirst(): Boolean {
        orderFirst.value = sharedPreferences.getBoolean("order_first", false)
        return orderFirst.value
    }

    fun setOderFirst(order: Boolean) {
        job?.cancel()
        job = viewModelScope.launch {
            sharedPreferences.edit().putBoolean("order_first", order).apply()
            orderFirst.value = order;
            getProducts()
        }
    }

    private fun getProducts() {
        _productsRepository.getProducts(orderAlpha.value, orderFirst.value)
    }

    fun markProduct(product: Product, mark: Boolean) {
        job?.cancel()
        job = viewModelScope.launch {
            _productsRepository.markProduct(product, mark)
            getProducts()
        }
    }

    fun addProduct(product: Product) {
        job?.cancel()
        job = viewModelScope.launch {
            _productsRepository.addProduct(product)
            getProducts()
            Log.v(
                "VM:add", "Cantidad de elementos ${_productsRepository.product.value.products.size}"
            )
        }
    }

    fun removeProduct(product: Product) {
        job?.cancel()
        job = viewModelScope.launch {
            _productsRepository.removeProduct(product)
            Log.v(
                "VM:remove",
                "Cantidad de elementos ${_productsRepository.product.value.products.size}"
            )
        }
    }

    fun saveProductsOnDisc(fileOutputStream: FileOutputStream) {
        job?.cancel()
        job = viewModelScope.launch {
            _productsRepository.saveProductsOnDisc(fileOutputStream)
        }
    }

    fun getProductsOnDisc(fileInputStream: FileInputStream) {
        job?.cancel()
        job = viewModelScope.launch {
            _productsRepository.getProductsOnDisc(fileInputStream)
            getProducts()
        }
    }


}