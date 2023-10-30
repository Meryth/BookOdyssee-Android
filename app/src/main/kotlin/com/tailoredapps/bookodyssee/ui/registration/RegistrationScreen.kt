package com.tailoredapps.bookodyssee.ui.registration

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold

@Composable
fun RegistrationScreen(
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->

    }
}

@Composable
fun RegistrationView(
) {
}

@Preview(showBackground = true)
@Composable
private fun RegistrationPreview() {
    RegistrationView()
}