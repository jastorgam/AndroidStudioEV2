package cl.jam.p2_evaluacion2.data.models

import java.io.Serializable
import java.util.UUID

data class Product(
    val name: String,
    var marked: Boolean = false,
    val id: String = UUID.randomUUID().toString(),
) : Serializable