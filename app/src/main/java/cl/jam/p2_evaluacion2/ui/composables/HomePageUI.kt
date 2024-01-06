package cl.jam.p2_evaluacion2.ui.composables

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.jam.p2_evaluacion2.R
import cl.jam.p2_evaluacion2.data.models.Product
import cl.jam.p2_evaluacion2.ui.viewmodels.ProductsVM


@Preview(locale = "es")
@Composable
fun HomePageUI(
    vm: ProductsVM = viewModel(), onButtonSettingsClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    vm.initSharedPreferences(sharedPreferences)

    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val txtHomeTitle = context.getString(R.string.home_tittle)
    val txtLogo = context.getString(R.string.txt_logo)

    Scaffold(topBar = {
        AppShoppingTopBar(
            title = txtHomeTitle,
            onButtonSettingsClicked = onButtonSettingsClicked,
            showBackButton = false,
            showSettingsButton = true
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            /* Si la horientacion en landscape no se muetra la imagen
               ya que queda poco espacio
             */
            val orientation = context.resources.configuration.orientation

            if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
                Image(
                    painter = painterResource(id = R.drawable.carrito),
                    contentDescription = txtLogo,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(130.dp)
                )
            }

            // Formulario de entrada de nuevo registro de producto
            ProductFormUI(onClickAddProduct = { vm.addProduct(Product(it)) })

            Spacer(modifier = Modifier.height(20.dp))

            // Listado de productos
            ProductListUI(products = uiState.products,
                onDelete = { vm.removeProduct(it) },
                onChange = { p, v ->
                    vm.markProduct(p, v)
                })
        }
    }
}
