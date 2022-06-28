package com.example.bookstore.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookstore.model.MBook
import com.example.bookstore.repository.FireRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val fireRepository: FireRepository) : ViewModel() {

    val fAuth = FirebaseAuth.getInstance()
    var books: List<MBook> by mutableStateOf(emptyList())
    var loading:Boolean by mutableStateOf(true)

    init {
        getAllBooks()
    }

    fun logOut(onSuccess: () -> Unit){
        viewModelScope.launch {
            Log.d("Logout","Initiated Logout")
            fAuth.signOut().run {
                onSuccess.invoke()
            }
        }
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            books = fireRepository.getAllBooksFromDatabase().data!!.filter {
                it.userId == fAuth.currentUser?.uid.toString()
            }
            loading = false
        }
    }
}