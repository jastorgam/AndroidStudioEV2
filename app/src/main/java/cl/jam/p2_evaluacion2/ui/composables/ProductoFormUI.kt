package cl.jam.p2_evaluacion2.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cl.jam.p2_evaluacion2.R
import cl.jam.p2_evaluacion2.data.models.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormUI(
    onClickAddProduct: (product: String) -> Unit
) {
    val context = LocalContext.current
    val textProductPlaceholder = context.getString(R.string.producto_form_placeholder)
    val textButtonAddProduct = context.getString(R.string.producto_form_add)
    
    val (product, setProduct) = rememberSaveable {
        mutableStateOf("")
    }
    Box(
        contentAlignment = Alignment.CenterEnd, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        TextField(
            value = product,
            onValueChange = {
                setProduct(it)
            },
            placeholder = { Text(textProductPlaceholder) },
            modifier = Modifier.fillMaxWidth()
        )
        TooltipBox(
            positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
            tooltip = { PlainTooltip { Text(textButtonAddProduct) } },
            state = rememberTooltipState()
        ) {
            IconButton(onClick = {
                onClickAddProduct(product)
                setProduct("")
            }) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = textButtonAddProduct,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}