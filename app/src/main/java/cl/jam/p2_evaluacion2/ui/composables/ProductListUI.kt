package cl.jam.p2_evaluacion2.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.jam.p2_evaluacion2.R
import cl.jam.p2_evaluacion2.data.models.Product
import cl.jam.p2_evaluacion2.ui.viewmodels.ProductsVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListUI(
    products: List<Product>,
    onDelete: (p: Product) -> Unit,
    onChange: (p: Product, v: Boolean) -> Unit
) {
    val context = LocalContext.current
    val txtDeleteProduct = context.getString(R.string.delete_product)
    val txtDeleteProductTooltip = context.getString(R.string.product_list_delete_tooltip)

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    if (product.marked)
//                        Text(
//                            text = product.name,
//                            fontSize = 20.sp,
//                            modifier = Modifier
//                                .weight(2.0f)
//                                .padding(10.dp, 8.dp),
//                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
//                        ) else
//                        Text(
//                            text = product.name,
//                            fontSize = 20.sp,
//                            modifier = Modifier
//                                .weight(2.0f)
//                                .padding(10.dp, 8.dp),
//                        )
                    Text(
                        text = product.name,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .weight(2.0f)
                            .padding(10.dp, 8.dp),
                        style = if (product.marked) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                    )
                    Checkbox(
                        checked = product.marked,
                        onCheckedChange = { onChange(product, it) }
                    )
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                        tooltip = { PlainTooltip { Text(txtDeleteProductTooltip) } },
                        state = rememberTooltipState()
                    ) {
                        IconButton(onClick = { onDelete(product) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = txtDeleteProduct,
                                tint = Color.Black,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }
}