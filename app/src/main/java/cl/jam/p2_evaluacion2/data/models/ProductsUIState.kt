package cl.jam.p2_evaluacion2.data.models

data class ProductsUIState(
    val msg: String = "",
    val products: List<Product> = listOf()
)