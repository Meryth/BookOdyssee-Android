package com.tailoredapps.bookodyssee.ui.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.button.PrimaryButton
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme
import com.tailoredapps.bookodyssee.core.local.ReadingState
import com.tailoredapps.bookodyssee.core.model.BookImageLinks
import com.tailoredapps.bookodyssee.core.model.VolumeInfo
import com.tailoredapps.bookodyssee.ui.book.items.DataRow
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookScreen(
    bookId: String,
    viewModel: BookViewModel = getViewModel { parametersOf(bookId) }
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    val volumeInfo = state.bookItem?.volumeInfo

    if (volumeInfo != null) {
        BookView(
            bookVolume = volumeInfo,
            scrollState = scrollState,
            readingState = state.readingState,
            onAddToListClick = { viewModel.dispatch(BookViewModel.Action.AddBookToReadingList) },
            onUpdateReadingState = { viewModel.dispatch(BookViewModel.Action.ChangeReadingState(it)) },
            onRemoveFromListClick = { viewModel.dispatch(BookViewModel.Action.RemoveBookFromReadingList) }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun BookView(
    bookVolume: VolumeInfo,
    scrollState: ScrollState,
    readingState: ReadingState,
    onAddToListClick: () -> Unit,
    onUpdateReadingState: (ReadingState) -> Unit,
    onRemoveFromListClick: () -> Unit
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
                .padding(horizontal = AppTheme.dimens.dimen24)
        ) {
            Text(
                text = bookVolume.title,
                style = AppTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
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

            DataRow(field = stringResource(R.string.lb_author), value = bookVolume.authors)
            DataRow(field = stringResource(R.string.lb_publisher), value = bookVolume.publisher)
            DataRow(field = stringResource(R.string.lb_year), value = bookVolume.publishedDate)
            DataRow(
                field = stringResource(R.string.lb_page_count),
                value = bookVolume.pageCount.toString()
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppTheme.dimens.dimen12)
            )

            Text(
                text = bookVolume.description,
                modifier = Modifier.padding(bottom = AppTheme.dimens.dimen24)
            )

            Spacer(modifier = Modifier.weight(1f))

            if (readingState == ReadingState.NOT_ADDED) {
                PrimaryButton(
                    btnText = stringResource(R.string.btn_add_to_list),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = AppTheme.dimens.dimen24),
                    onClick = onAddToListClick
                )
            } else {
                if (readingState != ReadingState.FINISHED) {

                    if (readingState != ReadingState.CURRENTLY_READING) {
                        PrimaryButton(
                            btnText = stringResource(R.string.btn_start_reading),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = AppTheme.dimens.dimen24),
                            onClick = { onUpdateReadingState(ReadingState.CURRENTLY_READING) }
                        )
                    }

                    PrimaryButton(
                        btnText = stringResource(R.string.btn_finish),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppTheme.dimens.dimen24),
                        onClick = { onUpdateReadingState(ReadingState.FINISHED) }
                    )
                }

                PrimaryButton(
                    btnText = stringResource(R.string.btn_remove_from_list),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = AppTheme.dimens.dimen24),
                    onClick = onRemoveFromListClick
                )
            }
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
        scrollState = ScrollState(0),
        readingState = ReadingState.FINISHED,
        onAddToListClick = {},
        onUpdateReadingState = {},
        onRemoveFromListClick = {}
    )
}
