package com.tailoredapps.bookodyssee.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.bar.AppNavigationBar
import com.tailoredapps.bookodyssee.base.ui.layout.BookItem
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme
import com.tailoredapps.bookodyssee.core.local.LocalBook
import com.tailoredapps.bookodyssee.core.local.ReadingState
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel(),
    onBookItemClick: (id: String) -> Unit,
    onAddClick: () -> Unit,
    onToReadClick: () -> Unit,
    onFinishedClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(HomeViewModel.Action.GetSavedBooks)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        HomeView(
            navController = navController,
            toReadList = state.toReadList,
            currentlyReadingList = state.currentlyReadingList,
            onBookItemClicked = onBookItemClick,
            onAddClick = onAddClick,
            onToReadClick = onToReadClick,
            onFinishedClick = onFinishedClick
        )
    }
}

@Composable
private fun HomeView(
    navController: NavController,
    toReadList: List<LocalBook>,
    currentlyReadingList: List<LocalBook>,
    onBookItemClicked: (id: String) -> Unit,
    onAddClick: () -> Unit,
    onToReadClick: () -> Unit,
    onFinishedClick: () -> Unit
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name),
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "btnAdd",
                    tint = AppTheme.colors.onPrimary
                )
            }
        },
        bottomBar = {
            AppNavigationBar(
                navController = navController,
                onToReadClick = onToReadClick,
                onFinishedClick = onFinishedClick
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)

        ) {

            item {
                Text(
                    text = stringResource(R.string.lb_currently_reading),
                    color = AppTheme.colors.primary,
                    modifier = Modifier.padding(
                        start = AppTheme.dimens.dimen24,
                        top = AppTheme.dimens.dimen16
                    )
                )

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppTheme.dimens.dimen6)
                )
            }

            items(currentlyReadingList) { book ->
                BookItem(
                    bookId = book.bookId,
                    title = book.title,
                    authorList = book.authors,
                    imageUrl = book.imageLink,
                    modifier = Modifier.padding(
                        horizontal = AppTheme.dimens.dimen24,
                        vertical = AppTheme.dimens.dimen6
                    ),
                    onBookClick = { onBookItemClicked(book.bookId) }
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppTheme.dimens.dimen6)
                )

            }

            item {
                Text(
                    text = stringResource(R.string.lb_to_read),
                    color = AppTheme.colors.primary,
                    modifier = Modifier.padding(
                        start = AppTheme.dimens.dimen24,
                        top = AppTheme.dimens.dimen16
                    )
                )

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppTheme.dimens.dimen6)
                )
            }

            items(toReadList) { book ->
                BookItem(
                    bookId = book.bookId,
                    title = book.title,
                    authorList = book.authors,
                    imageUrl = book.imageLink,
                    modifier = Modifier.padding(
                        horizontal = AppTheme.dimens.dimen24,
                        vertical = AppTheme.dimens.dimen6
                    ),
                    onBookClick = { onBookItemClicked(book.bookId) }
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

@Preview
@Composable
private fun HomePreview() {
    AppTheme {
        HomeView(
            navController = rememberNavController(),
            toReadList = listOf(
                LocalBook(
                    userId = 0,
                    bookId = "id",
                    title = "title",
                    authors = listOf("author"),
                    publisher = "publisher",
                    publishedDate = "publishedDate",
                    pageCount = 200,
                    imageLink = "image"
                )
            ),
            currentlyReadingList = listOf(
                LocalBook(
                    userId = 0,
                    bookId = "id",
                    title = "title",
                    authors = listOf("author"),
                    publisher = "publisher",
                    publishedDate = "publishedDate",
                    pageCount = 200,
                    imageLink = "image",
                    readingState = ReadingState.CURRENTLY_READING
                )
            ),
            onBookItemClicked = {},
            onAddClick = {},
            onToReadClick = {},
            onFinishedClick = {}
        )
    }
}
