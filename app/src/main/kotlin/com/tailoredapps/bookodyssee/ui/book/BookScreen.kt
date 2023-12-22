package com.tailoredapps.bookodyssee.ui.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme
import com.tailoredapps.bookodyssee.core.model.BookImageLinks
import com.tailoredapps.bookodyssee.core.model.VolumeInfo

@Composable
fun BookScreen(bookId: String) {

    val scrollState = rememberScrollState()

    
}

@Composable
fun BookView(
    bookVolume: VolumeInfo,
    scrollState: ScrollState,
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = AppTheme.dimens.dimen24)
        ) {
            Text(
                text = bookVolume.title,
                style = AppTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = AppTheme.dimens.dimen16)
            )

            if (!bookVolume.imageLinks?.thumbnail.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(bookVolume.imageLinks?.thumbnail),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = AppTheme.dimens.dimen16)
                        .size(
                            width = AppTheme.dimens.dimen100,
                            height = AppTheme.dimens.dimen140
                        )
                        .align(Alignment.CenterHorizontally)
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppTheme.dimens.dimen24)
            )

            DataRow(field = "Author", value = bookVolume.authors)
            DataRow(field = "Publisher", value = bookVolume.publisher)
            DataRow(field = "Year", value = bookVolume.publishedDate)

            //TODO: check if book can be found in room database
            // if yes -> show reading/to read/finished
            // if not -> show "add to list" button
            DataRow(field = "State", value = "TODO")
            DataRow(field = "Page Count", value = bookVolume.pageCount.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookViewPreview() {
    BookView(
        bookVolume = VolumeInfo(
            title = "101 dad jokes",
            authors = listOf("Florian Mötz"),
            publisher = "Diamir",
            publishedDate = "18.12.2023",
            description = "Jokes",
            pageCount = 101,
            imageLinks = BookImageLinks("asd")
        ),
        scrollState = ScrollState(0)
    )
}
