package cl.jam.p2_evaluacion2.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cl.jam.p2_evaluacion2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShoppingTopBar(
    title: String = "",
    showSettingsButton: Boolean = true,
    showBackButton: Boolean = false,
    onButtonSettingsClicked: () -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val txtSettings = context.getString(R.string.settings)
    val txtBack = context.getString((R.string.back))

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = {
                    onBackButtonClicked()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = txtBack
                    )
                }
            }
        },
        actions = {
            if (showSettingsButton) {
                IconButton(onClick = {
                    onButtonSettingsClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = txtSettings
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}