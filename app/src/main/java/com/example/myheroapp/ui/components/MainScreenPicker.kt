package com.example.myheroapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myheroapp.R
import com.example.myheroapp.utils.getPublisherImg

private const val TAG = "MainScreenPicker"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Picker(
    searchIsActive: Boolean,
    updateSearchIsActive: () -> Unit,
    publishersList: List<String>,
    showingFromPublisher: String,
    onSearchQuery: (String?) -> Unit,
    onPublisherClick: (String) -> Unit,
    onFavoritesClick: () -> Unit,
    isFavoriteFilterOn: Boolean,
    modifier: Modifier = Modifier
){
    val searchQuery = remember {
        mutableStateOf("")
    }
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        SearchBar(
            query = if (searchIsActive) searchQuery.value else "",
            onQueryChange = {
                searchQuery.value = it
                onSearchQuery(searchQuery.value)
            },
            onSearch = {},
            active = searchIsActive,
            onActiveChange = { updateSearchIsActive() },
            placeholder = {
                if (showingFromPublisher == "" && !isFavoriteFilterOn) {
                    Text(text = stringResource(id = R.string.search_placeholder))
                } else if (showingFromPublisher == "" && isFavoriteFilterOn) {
                    Text(text = stringResource(id = R.string.show_all_favorites))
                } else if (showingFromPublisher != "" && isFavoriteFilterOn) {
                    Text(text = stringResource(R.string.show_favorites_from)+" "+showingFromPublisher)
                } else {
                    Text(text = showingFromPublisher)
                }
            },
            trailingIcon = {
                if (searchIsActive){
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.collapse),
                        modifier = Modifier.rotate(180F)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.expand)
                    )
                }
            },
            colors = SearchBarDefaults.colors(containerColor = colorResource(R.color.searchbar_bg)),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ){
                LazyColumn(
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    item { Spacer(modifier = Modifier.height(40.dp)) }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable { onPublisherClick("") },
                            contentAlignment = Alignment.Center
                        ){
                            Row {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                                Text(text = stringResource(id = R.string.show_all_publishers))
                            }
                        }
                    }
                    items(publishersList){item ->
                        PublisherItem(
                            publisherName = item,
                            publisherImg = getPublisherImg(item),
                            onClick = {onPublisherClick(it)})
                    }
                }
                FavoritesFilterItem(
                    onClick = { onFavoritesClick() },
                    isFilterOn = isFavoriteFilterOn
                )
            }
        }
    }
}



@Preview
@Composable
private fun PickerPreview(){
    Picker(
        publishersList = listOf("Marvel", "DC Comics"),
        onSearchQuery = {},
        onPublisherClick = {},
        onFavoritesClick = {},
        isFavoriteFilterOn = true,
        searchIsActive = true,
        updateSearchIsActive = {},
        showingFromPublisher = ""
    )
}
