package com.tailoredapps.bookodyssee.base.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme

@Composable
fun BookItem(
    title: String,
    authorList: List<String>?,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(end = AppTheme.dimens.dimen12)
                .size(
                    width = AppTheme.dimens.dimen64,
                    height = 100.dp
                )
        )

        Column(verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.dimen4)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            authorList?.onEach { author ->
                Text(text = author, style = AppTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookItemPreview() {
    BookItem(
        title = "Mcdonald's Einstieg",
        authorList = listOf("Lebensmüder Student"),
        imageUrl = "mcdonalds.png"
    )
}