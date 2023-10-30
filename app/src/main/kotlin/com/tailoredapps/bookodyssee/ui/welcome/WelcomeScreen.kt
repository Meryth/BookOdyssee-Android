package com.tailoredapps.bookodyssee.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme

@Composable
fun WelcomeScreen() {
    WelcomeView()
}

@Composable
fun WelcomeView(
) {
    AppScaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = AppTheme.dimens.dimen24)
        ) {
            Text(
                text = stringResource(R.string.welcome_title),
                style = AppTheme.typography.headlineLarge,
                color = AppTheme.colors.onSurfaceVariant,
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo_welcome_book),
                contentDescription = null,
                modifier = Modifier.padding(vertical = AppTheme.dimens.dimen12)
            )

            Text(
                text = stringResource(R.string.welcome_subtitle),
                style = AppTheme.typography.labelMedium,
                color = AppTheme.colors.onSurfaceVariant
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.dimen48),
                shape = AppTheme.shapes.extraSmall,
                onClick = { /*TODO*/ }) {

                Text(
                    text = stringResource(R.string.btn_login),
                    modifier = Modifier.padding(vertical = AppTheme.dimens.dimen4)
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.dimen24),
                shape = AppTheme.shapes.extraSmall,
                onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(R.string.btn_register),
                    modifier = Modifier.padding(vertical = AppTheme.dimens.dimen4)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomePreview() {
    WelcomeView()
}