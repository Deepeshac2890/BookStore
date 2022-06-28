package com.example.bookstore.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bookstore.component.ConfirmationDialog
import com.example.bookstore.component.HomeSectionHeader
import com.example.bookstore.component.ListCard
import com.example.bookstore.model.MBook
import com.example.bookstore.navigation.Screens
import com.example.bookstore.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        HomeTopAppBar(accountAction = {
            navController.navigate(Screens.ReaderStatsScreen.name)
        }) {
            viewModel.logOut {
                navController.navigate(Screens.SplashScreen.name)
            }
        }
    }, floatingActionButton = {
        FloatingButton {
            navController.navigate(Screens.SearchScreen.name)
        }
    }) {
        HomeContent(viewModel = viewModel)
    }
}

@Composable
fun HomeContent(viewModel: HomeScreenViewModel) {
    var books: List<MBook> = viewModel.books
//    var showConfirmation = remember {
//        mutableStateOf<String?>(null)
//    }

//    if (showConfirmation.value != null) {
//        ConfirmationDialog(bookId = showConfirmation.value!!, onRead = {}, onDismiss = {
//            showConfirmation.value = null
//        }){
//
//        }
//    }
    Column(modifier = Modifier.fillMaxSize()) {
        HomeSectionHeader(label = "Continue Reading")
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(){
            items(2){
                ListCard()
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        HomeSectionHeader(label = "Reading List")
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(){
            items(books){ item ->
                ListCard(item)
            }
        }
    }
}

@Composable
fun FloatingButton(onTap: () -> Unit) {
    FloatingActionButton(onClick = onTap, backgroundColor = Constants.PrimaryThemeColor) {
        Icon(Icons.Default.Search, "Search Button", tint = Color.White)
    }
}

@Composable
fun HomeTopAppBar(accountAction: () -> Unit,logOut: () -> Unit) {
    TopAppBar(backgroundColor = Constants.PrimaryThemeColor) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = accountAction) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account Icon"
                )
            }
            IconButton(onClick = logOut) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout Icon"
                )
            }
        }
    }
}
