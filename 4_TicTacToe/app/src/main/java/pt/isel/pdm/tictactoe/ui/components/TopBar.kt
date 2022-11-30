package pt.isel.pdm.tictactoe.ui.components


import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pt.isel.pdm.tictactoe.R

@Composable
fun TopBar(
    showSettings: (() -> Unit)? = null,
    showRefresh: (() -> Unit)? = null,
    backButtonClicked: (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,

    ) {
    TopAppBar(
        title = {
            if (title == null)
                Text(text = stringResource(id = R.string.app_name))
            else
                title()
        },
        actions = {

            if (showSettings != null)
                IconButton(onClick = { showSettings() }) {
                    Icon(Icons.Default.Settings, null)
                }
            if (showRefresh != null)
                IconButton(onClick = { showRefresh() }) {
                    Icon(Icons.Default.Refresh, null)
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