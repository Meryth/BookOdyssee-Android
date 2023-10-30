package com.tailoredapps.bookodyssee.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.R
import com.tailoredapps.bookodyssee.base.ui.scaffold.AppScaffold
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    onListElementClicked: (id: Int) -> Unit
) {
    val viewModelState by viewModel.state.collectAsState()

    HomeView(
        title = stringResource(id = R.string.app_name),
        onListElementClicked = onListElementClicked
    )
}

@Composable
private fun HomeView(
    title: String,
    onListElementClicked: (id: Int) -> Unit
) {
    AppScaffold(title = title) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding)
        ) {
            items((0..500).toList()) {
                Text(
                    text = it.toString(),
                    modifier = Modifier
                        .padding(AppTheme.dimens.dimen16)
                        .fillMaxWidth()
                        .clickable { onListElementClicked(it) }
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
            title = stringResource(id = R.string.app_name),
            onListElementClicked = {}
        )
    }
}

