package com.tailoredapps.bookodyssee.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.button.PrimaryButton
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.text.DefaultTextField
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = getViewModel()
) {
    val state by viewModel.state.collectAsState()

    RegistrationView(
        username = state.username,
        password = state.password,
        confirmPassword = state.confirmPassword,
        onUsernameChange = { viewModel.dispatch(RegistrationViewModel.Action.ChangeUsername(it)) },
        onPasswordChange = { viewModel.dispatch(RegistrationViewModel.Action.ChangePassword(it)) },
        onConfirmPasswordChange = {
            viewModel.dispatch(
                RegistrationViewModel.Action.ChangeConfirmPassword(it)
            )
        },
        onRegisterClick = {}
    )
}

@Composable
fun RegistrationView(
    username: String,
    password: String,
    confirmPassword: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                AppTheme.dimens.dimen48,
                Alignment.CenterVertically
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = AppTheme.dimens.dimen24)
        ) {
            DefaultTextField(
                value = username,
                label = stringResource(R.string.lb_username),
                singleLine = true,
                onValueChange = { onUsernameChange(it) }
            )
            DefaultTextField(
                value = password,
                label = stringResource(R.string.lb_password),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { onPasswordChange(it) }
            )
            DefaultTextField(
                value = confirmPassword,
                label = stringResource(R.string.lb_password_confirm),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { onConfirmPasswordChange(it) }
            )

            PrimaryButton(
                btnText = stringResource(id = R.string.btn_register),
                onClick = onRegisterClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationPreview() {
    RegistrationView(
        username = "username",
        password = "password",
        confirmPassword = "password",
        onUsernameChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {}
    )
}