package pt.isel.pdm.imageoftheday.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pt.isel.pdm.imageoftheday.R

@Composable
fun TopBar(
    showInfoScreen: (() -> Unit)? = null,
    backButtonClicked: (() -> Unit)? = null

) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {

            if (showInfoScreen != null)
                IconButton(onClick = { showInfoScreen() }) {
                    Icon(Icons.Default.Info, null)
                }
        },
        navigationIcon = {
            if (backButtonClicked != null)
                IconButton(onClick = { backButtonClicked() }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
        }
    )
}
