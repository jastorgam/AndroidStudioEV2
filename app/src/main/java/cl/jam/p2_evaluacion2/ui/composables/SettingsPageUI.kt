package cl.jam.p2_evaluacion2.ui.composables

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.jam.p2_evaluacion2.R
import cl.jam.p2_evaluacion2.ui.viewmodels.ProductsVM


@Preview(locale = "es")
@Composable
fun SettingsPageUI(
    vm: ProductsVM = viewModel(),
    onBackButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    val txtSettingsTitle = context.getString(R.string.settings_tittle)
    val txtSettingsOrder = context.getString(R.string.settings_order)
    val txtSettingsSort = context.getString(R.string.settings_sort)


    Scaffold(
        topBar = {
            AppShoppingTopBar(
                title = txtSettingsTitle,
                onBackButtonClicked = onBackButtonClicked,
                showBackButton = true,
                showSettingsButton = false,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 10.dp,
                    vertical = it.calculateTopPadding()
                )
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(txtSettingsOrder, fontSize = 16.sp)
                Switch(
                    checked = vm.getOrderAlpha(),
                    onCheckedChange = {
                        vm.setOderAlpha(it)
                    })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(txtSettingsSort, fontSize = 16.sp)
                Switch(
                    checked = vm.getOrderFirst(),
                    onCheckedChange = {
                        vm.setOderFirst(it)
                    })
            }
        }
    }
}