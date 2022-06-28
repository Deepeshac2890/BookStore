package com.example.bookstore.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bookstore.component.TopAppBarWithTitle

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getBookDetails(bookId = bookId)
    }
    Scaffold(topBar = {
        TopAppBarWithTitle(label = "Book Details") {
            navController.popBackStack()
        }
    }) {
            BookDetails(viewModel, navController = navController)
    }
}

@Composable
fun BookDetails(viewModel: DetailsViewModel,navController: NavController) {
    var bookData = viewModel.bookItem
    var verticalScroll = rememberScrollState()
    var loading = viewModel.loading

    if (loading) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
    else {
        Surface(Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScroll),
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Card(elevation = 5.dp, shape = RoundedCornerShape(10.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(model = bookData?.volumeInfo?.imageLinks?.thumbnail),
                            contentDescription = "Book Image",
                            modifier = Modifier
                                .width(300.dp)
                                .height(300.dp),
                            alignment = Alignment.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(bookData?.volumeInfo?.title.toString())
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.LightGray
                        )
                    ) {
                        append("\nby ")
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.LightGray
                        )
                    ) {
                        append(bookData?.volumeInfo?.authors.toString())
                    }
                })
                Spacer(modifier = Modifier.height(3.dp))
                Text("Published on : ${bookData?.volumeInfo?.publishedDate}")
                Spacer(modifier = Modifier.height(3.dp))
                Text("Number of Pages : ${bookData?.volumeInfo?.pageCount}")
                Spacer(modifier = Modifier.height(3.dp))
                Text("Description : ")
                Spacer(modifier = Modifier.height(2.dp))
                Text(

                    HtmlCompat.fromHtml(
                        bookData?.volumeInfo?.description.toString(),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString(),  modifier = Modifier
                        .weight(0.6f)
                        .verticalScroll(rememberScrollState()), overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(modifier= Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {}, shape = RoundedCornerShape(10.dp)) {
                        Text("Start Reading")
                    }
                    Button(onClick = {
                        viewModel.addBookToFireStore{
                            navController.popBackStack()
                        }
                    }, shape = RoundedCornerShape(10.dp)) {
                        Text("Add to Reading List")
                    }
                }
            }
        }
    }
}