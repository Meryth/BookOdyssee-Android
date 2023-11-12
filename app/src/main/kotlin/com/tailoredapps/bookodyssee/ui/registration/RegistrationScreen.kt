package com.tailoredapps.bookodyssee.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel

@Composable
fun RegistrationScreen(
    onRegistrationSuccess: () -> Unit,
    viewModel: RegistrationViewModel = getViewModel()
) {
    val state by viewModel.state.collectAsState()

    RegistrationView(
        username = state.username,
        password = state.password,
        confirmPassword = state.confirmPassword,
        isError = state.isError,
        onUsernameChange = { viewModel.dispatch(RegistrationViewModel.Action.ChangeUsername(it)) },
        onPasswordChange = { viewModel.dispatch(RegistrationViewModel.Action.ChangePassword(it)) },
        onConfirmPasswordChange = {
            viewModel.dispatch(
                RegistrationViewModel.Action.ChangeConfirmPassword(it)
            )
        },
        onRegisterClick = {
            viewModel.dispatch(RegistrationViewModel.Action.OnRegisterClick)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.effects.onEach { effect ->
            when (effect) {
                is RegistrationViewModel.Effect.IsSuccess -> onRegistrationSuccess()
            }
        }.launchIn(this)
    }
}

@Composable
fun RegistrationView(
    username: String,
    password: String,
    isError: Boolean,
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

            if (isError) {
                Text(
                    text = stringResource(R.string.registration_error),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

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
        isError = false,
        onUsernameChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {}
    )
}