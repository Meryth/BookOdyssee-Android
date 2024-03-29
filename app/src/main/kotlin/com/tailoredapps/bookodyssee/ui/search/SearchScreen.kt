package com.tailoredapps.bookodyssee.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.layout.BookItem
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme
import com.tailoredapps.bookodyssee.core.model.BookItem
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = getViewModel(),
    onBookClick : (bookId: String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    SearchView(
        searchQuery = state.query,
        searchResult = state.searchResult,
        onQueryChange = { viewModel.dispatch(SearchViewModel.Action.OnQueryChange(it)) },
        onSearchClick = { viewModel.dispatch(SearchViewModel.Action.OnSearchClick(it)) },
        onBookClick = onBookClick
    )
}

@Composable
fun SearchView(
    searchQuery: String,
    searchResult: List<BookItem>,
    onQueryChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onBookClick: (String) -> Unit,
) {
    AppScaffold(
        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
                .padding(AppTheme.dimens.dimen24)
        ) {
            SearchBar(
                searchTerm = searchQuery,
                onSearchChange = onQueryChange,
                onSearchClick = onSearchClick,
                modifier = Modifier.padding(bottom = AppTheme.dimens.dimen24)
            )

            LazyColumn() {
                items(searchResult) { book ->
                    BookItem(
                        bookId = book.id,
                        title = book.volumeInfo.title,
                        authorList = book.volumeInfo.authors,
                        imageUrl = book.volumeInfo.imageLinks?.thumbnail,
                        modifier = Modifier.padding(vertical = AppTheme.dimens.dimen6),
                        onBookClick = onBookClick
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = AppTheme.dimens.dimen6)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchTerm: String,
    onSearchChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = searchTerm,
        onValueChange = { value ->
            onSearchChange(value)
        },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(R.string.search_hint),
                color = AppTheme.colors.outline
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = AppTheme.colors.onBackground,
            cursorColor = AppTheme.colors.primary,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = AppTheme.colors.outline
            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick.invoke(searchTerm)
            }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar(
        searchTerm = "",
        onSearchChange = {},
        onSearchClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    SearchView(
        searchQuery = "search",
        searchResult = emptyList(),
        onQueryChange = {},
        onSearchClick = {},
        onBookClick = {}
    )
}