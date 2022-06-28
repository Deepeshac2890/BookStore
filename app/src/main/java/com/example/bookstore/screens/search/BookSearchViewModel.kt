package com.example.bookstore.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.model.Item
import com.example.bookstore.repository.BooksRepository
import com.example.composestarterproject.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BooksRepository) :
    ViewModel() {
    var loading: Boolean by mutableStateOf(false)
    var list: List<Item> by mutableStateOf(listOf())
    var error: Boolean by mutableStateOf(false)

    // Not Sure why this did not work  !!!
    // var data = mutableStateOf(
    //      DataOrException(null,false,Exception(""))
    // )
    fun searchBooks(query: String) {
        viewModelScope.launch() {
            try{
                error = false
                if (query.isEmpty()) {
                    return@launch
                }
                loading = true
                list = repository.getAllBooks(q = query).data!!
                if (list != null && list.isNotEmpty()) {
                    loading = false
                }
            }
            catch (e: Exception) {
                Log.d("Exception", e.toString())
                list = emptyList()
                error = true
            }

        }
    }
}