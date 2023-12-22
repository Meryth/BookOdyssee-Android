package com.tailoredapps.bookodyssee.ui.book.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme

@Composable
fun DataRow(
    field: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimens.dimen2),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = field,
            color = AppTheme.colors.primary
        )
        Text(text = value)
    }
}

@Composable
fun DataRow(
    field: String,
    value: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimens.dimen2),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = field,
            color = AppTheme.colors.primary
        )

        var fullValueList = ""

        value.forEach { value ->
            fullValueList += if (fullValueList.isEmpty()) {
                value
            } else {
                ", $value"
            }
        }
        Text(text = fullValueList)
    }
}

@Preview(showBackground = true)
@Composable
fun DataRowPreview() {
    DataRow(field = "Author", value = "Bob")
}

@Preview(showBackground = true)
@Composable
fun DataRowListPreview() {
    DataRow(field = "Author", value = listOf("Alice", "Bob", "Charles"))
}

