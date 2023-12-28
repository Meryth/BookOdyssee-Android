package com.tailoredapps.bookodyssee.ui.finished

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.layout.BookItem
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme
import com.tailoredapps.bookodyssee.core.local.LocalBook
import com.tailoredapps.bookodyssee.core.local.ReadingState
import org.koin.androidx.compose.getViewModel

@Composable
fun FinishedScreen(
    viewModel: FinishedViewModel = getViewModel(),
    onBookItemClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(FinishedViewModel.Action.GetFinishedBooks)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        FinishedView(
            bookList = state.finishedBookList,
            onBookItemClick = onBookItemClick
        )
    }
}

@Composable
fun FinishedView(
    bookList: List<LocalBook>,
    onBookItemClick: (String) -> Unit
) {
    AppScaffold(
        title = stringResource(R.string.title_finished)
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(top = AppTheme.dimens.dimen16)
        ) {
            items(bookList) { book ->
                BookItem(
                    bookId = book.bookId,
                    title = book.title,
                    authorList = book.authors,
                    imageUrl = book.imageLink,
                    modifier = Modifier.padding(
                        horizontal = AppTheme.dimens.dimen24,
                        vertical = AppTheme.dimens.dimen6
                    ),
                    onBookClick = { onBookItemClick(book.bookId) }
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppTheme.dimens.dimen6)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FinishedPreview() {
    FinishedView(
        bookList = listOf(
            LocalBook(
                userId = 0,
                bookId = "id",
                title = "title",
                authors = listOf("author"),
                publisher = "publisher",
                publishedDate = "publishedDate",
                pageCount = 200,
                imageLink = "image",
                readingState = ReadingState.FINISHED
            )
        ),
        onBookItemClick = {}
    )
}