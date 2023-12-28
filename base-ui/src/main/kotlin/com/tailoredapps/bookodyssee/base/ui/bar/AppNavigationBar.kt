package com.tailoredapps.bookodyssee.base.ui.bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee.base.ui.theme.AppTheme

sealed class NavigationItem(
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
    val label: String
) {
    data object Maps : NavigationItem(
        outlinedIcon = Icons.Outlined.LocationOn,
        filledIcon = Icons.Filled.LocationOn,
        label = "Libraries"
    )

    data object ToRead : NavigationItem(
        outlinedIcon = Icons.Outlined.AutoStories,
        filledIcon = Icons.Filled.AutoStories,
        label = "To Read"
    )

    data object Finished :
        NavigationItem(
            outlinedIcon = Icons.Outlined.Book,
            filledIcon = Icons.Filled.Book,
            label = "Finished"
        )
}

@Composable
fun AppNavigationBar(
    onFinishedClick: () -> Unit,
) {

    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(NavigationItem.Maps, NavigationItem.ToRead, NavigationItem.Finished)

    NavigationBar(
        containerColor = AppTheme.colors.secondaryContainer,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    if (selectedItem == index) {
                        Icon(imageVector = item.filledIcon, contentDescription = null)
                    } else {
                        Icon(imageVector = item.outlinedIcon, contentDescription = null)
                    }
                },
                label = {
                    Text(text = item.label)
                },
                selected = selectedItem == index,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = AppTheme.colors.primaryContainer,
                    selectedIconColor = AppTheme.colors.onPrimaryContainer,
                    selectedTextColor = AppTheme.colors.onPrimaryContainer,
                    unselectedIconColor = AppTheme.colors.secondary,
                    unselectedTextColor = AppTheme.colors.secondary
                ),
                enabled = selectedItem != index,
                onClick = {
                    selectedItem = index
                    //TODO: ask how to add nav with new navigation
                    onFinishedClick()
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationBarPreview() {
    AppTheme {
        AppNavigationBar(
            onFinishedClick = {}
        )
    }
}