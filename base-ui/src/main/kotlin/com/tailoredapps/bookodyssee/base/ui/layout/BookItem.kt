package com.tailoredapps.bookodyssee.base.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import coil.compose.rememberAsyncImagePainter
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme

@Composable
fun BookItem(
    bookId: String,
    title: String,
    authorList: List<String>?,
    imageUrl: String?,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onBookClick(bookId) },
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
                    height = AppTheme.dimens.dimen100
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
        bookId = "bookId",
        title = "Mcdonald's Einstieg",
        authorList = listOf("Lebensmüder Student"),
        imageUrl = "mcdonalds.png",
        onBookClick = {}
    )
}