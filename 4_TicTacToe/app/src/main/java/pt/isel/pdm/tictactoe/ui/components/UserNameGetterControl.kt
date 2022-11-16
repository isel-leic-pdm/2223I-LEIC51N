package pt.isel.pdm.tictactoe.ui.components

import android.widget.EditText
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.tictactoe.R

@Composable
fun UserNameGetterControl(
    userName: String = "",
    userNameChanged: ((String) -> Unit) = { },
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .padding(10.dp)
            .then(modifier)
    ) {
        Text(
            text = stringResource(id = R.string.user_name),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TextField(
            value = userName,
            onValueChange = userNameChanged,
            modifier = Modifier.align(Alignment.CenterHorizontally)

        )
    }
}


@Composable
@Preview
fun PreviewUserNameGetterControl() {
    UserNameGetterControl(
        userName = "testUserName",
        userNameChanged = {}
    )
}


@Composable
@Preview
fun PreviewUserNameGetterControlFullSize() {
    Box(modifier = Modifier.fillMaxSize())
    {
        UserNameGetterControl(
            userName = "testUserName",
            userNameChanged = {},
            modifier = Modifier.align(Alignment.Center)

        )
    }
}
