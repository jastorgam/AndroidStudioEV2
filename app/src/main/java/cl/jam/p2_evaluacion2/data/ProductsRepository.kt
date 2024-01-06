package cl.jam.p2_evaluacion2.data

import android.util.Log
import cl.jam.p2_evaluacion2.data.models.Product
import cl.jam.p2_evaluacion2.data.models.ProductsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ProductsRepository {

    private val _products = MutableStateFlow(ProductsUIState())
    val product: StateFlow<ProductsUIState> = _products

    companion object {
        const val FILE_NAME = "productos.data"
    }

    fun getProductsOnDisc(fileInputStream: FileInputStream) {
        val products = try {
            fileInputStream.use { fis ->
                ObjectInputStream(fis).use { ois ->
                    ois.readObject() as? List<Product> ?: emptyList()
                }
            }
        } catch (ex: Exception) {
            emptyList<Product>()
        }
        Log.v("Cargando data", products.size.toString())
        addProduct(*products.toTypedArray(), append = false)
    }

    fun saveProductsOnDisc(fileOutputStream: FileOutputStream) {
        try {
            Log.v("Guardando data", _products.value.products.size.toString())
            fileOutputStream.use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(_products.value.products)
                }
            }
        } catch (ex: Exception) {
            Log.e("ProductosRepository:guardar", ex.toString())
        }

    }

    fun addProduct(vararg products: Product, append: Boolean = true) {
        Log.v("Products Repository", "Agregando productos")

        val updatedProducts = if (append) ArrayList(_products.value.products) else ArrayList()
        updatedProducts.addAll(products.asList())

        this._products.update {
            it.copy(
                msg = if (products.size == 1) "Producto Agregado" else "Productos Agregados",
                products = updatedProducts
            )
        }
        _products.asStateFlow()
    }

    fun markProduct(product: Product, mark: Boolean) {
        Log.v("Products Repository", "Modificando la marca")

        var updatedProducts = ArrayList(_products.value.products)
        var idx = updatedProducts.indexOf(product)

        updatedProducts.remove(product)
        updatedProducts.add(idx, Product(product.name, mark))

        this._products.update {
            it.copy(
                msg = "Producto Modificado",
                products = updatedProducts
            )
        }
        _products.asStateFlow()
    }


    fun removeProduct(product: Product) {
        val updatedProducts = ArrayList(_products.value.products)
        updatedProducts.remove(product)

        _products.update {
            it.copy(
                msg = "Producto Eliminado", products = updatedProducts
            )
        }
        _products.asStateFlow()
    }

    fun getProducts(
        orderAlpha: Boolean = false, orderFirst: Boolean = false
    ): StateFlow<ProductsUIState> {
        var updatedProducts = _products.value.products
        if (orderAlpha && !orderFirst) {
            Log.v("Sort", "Ordenar por nombre")
            updatedProducts = updatedProducts.sortedWith(compareBy({ it.name }))
        } else if (orderFirst && !orderAlpha) {
            Log.v("Sort", "Ordenar por marca")
            updatedProducts = updatedProducts.sortedWith(compareBy({ it.marked }))
        } else if (orderAlpha && orderFirst) {
            Log.v("Sort", "Ordenar por nombre y marca")
            updatedProducts = updatedProducts.sortedWith(compareBy({ it.marked }, { it.name }))
        } else {
            Log.v("Sort", "Sin ordenar")
        }

        _products.update {
            it.copy(products = updatedProducts)
        }
        return _products.asStateFlow()
    }

}