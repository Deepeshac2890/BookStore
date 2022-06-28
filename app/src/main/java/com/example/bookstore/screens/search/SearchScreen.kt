package com.example.bookstore.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.bookstore.component.BookRating
import com.example.bookstore.component.InputField
import com.example.bookstore.component.RoundedButton
import com.example.bookstore.component.TopAppBarWithTitle
import com.example.bookstore.model.Item
import com.example.bookstore.model.MBook
import com.example.bookstore.navigation.Screens
import com.example.bookstore.utils.Constants


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: BookSearchViewModel = hiltViewModel()) {

    Scaffold(topBar = {
        TopAppBarWithTitle(label = "Search Books"){
            navController.popBackStack()
        }
    }) {
        Column {
            SearchForm { query ->
                viewModel.searchBooks(query = query)
            }
            BookList(navController = navController)
        }
    }
}

@Composable
fun BookList(viewModel: BookSearchViewModel = hiltViewModel(), navController: NavController) {
    var bookList = viewModel.list
    var loading = viewModel.loading
    var error = viewModel.error
    if (error) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
            Text("No Books Found !!", style= MaterialTheme.typography.h3)
        }
    }
    else if (loading) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
            CircularProgressIndicator()
        }
    } else if (bookList != null) {
        LazyColumn() {
            items(bookList!!) { book ->
                BookRow(book = book) {
                    navController.navigate(Screens.DetailScreen.name+"/${it}")
                }
            }
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun SearchForm(
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(valueState = searchQueryState,
            labelId = hint,
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}

@Composable
fun BookRow(
    book: Item, onPressDetails: (String) -> Unit = {}
) {
    Card(shape = RoundedCornerShape(29.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .height(140.dp)
            .fillMaxWidth()
            .clickable { onPressDetails.invoke(book.id) }) {
        Row(
            Modifier
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = book.volumeInfo?.imageLinks?.thumbnail),
                contentDescription = "book image",
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(5.dp))
            DetailsColumn(book)
        }

    }
}

@Composable
fun DetailsColumn(book: Item) {
    Column {
        Text(book.volumeInfo.title, style = MaterialTheme.typography.h6)
        Text("Author: ${book.volumeInfo.authors}", style = MaterialTheme.typography.caption)
        Text("Date: ${book.volumeInfo.publishedDate}", style = MaterialTheme.typography.caption)
        Text("Categories: ${book.volumeInfo.categories}", style = MaterialTheme.typography.caption)
    }
}
