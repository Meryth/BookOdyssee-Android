package com.tailoredapps.bookodyssee.base.ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme

@Composable
fun PrimaryButton(
    btnText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shapes.extraSmall,
    btnColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary
    )
) {
    Button(
        colors = btnColors,
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        onClick = onClick
    ) {
        Text(
            text = btnText,
            modifier = Modifier.padding(vertical = AppTheme.dimens.dimen6)
        )
    }
}
