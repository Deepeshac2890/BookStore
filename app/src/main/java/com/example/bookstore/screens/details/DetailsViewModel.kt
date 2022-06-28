package com.example.bookstore.screens.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.model.Item
import com.example.bookstore.model.MBook
import com.example.bookstore.repository.BooksRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {
    var bookItem:Item? by mutableStateOf(null)
    var loading:Boolean by mutableStateOf(true)

    fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            bookItem = repository.getParticularBook(bookId = bookId).data
            loading = false
            Log.d("Details", bookItem.toString())
        }
    }

    fun addBookToFireStore(action: () -> Unit){
        loading = true
        val book = MBook(
            title = bookItem?.volumeInfo?.title,
            authors = bookItem?.volumeInfo?.authors.toString(),
            description = bookItem?.volumeInfo?.description,
            categories = bookItem?.volumeInfo?.categories.toString(),
            notes = "",
            photoUrl = bookItem?.volumeInfo?.imageLinks?.thumbnail,
            publishedDate = bookItem?.volumeInfo?.publishedDate,
            pageCount = bookItem?.volumeInfo?.pageCount.toString(),
            rating = 0.0,
            googleBookId = bookItem?.id,
            userId = FirebaseAuth.getInstance().currentUser?.uid.toString())

        saveToFirebase(book, action = action)
    }

    private fun saveToFirebase(book: MBook, action: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val dbCollection = db.collection("books")
        if (book.toString().isNotEmpty()){
            dbCollection.add(book)
                .addOnSuccessListener { documentRef ->
                    val docId = documentRef.id
                    dbCollection.document(docId)
                        .update(hashMapOf("id" to docId) as Map<String, Any>)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                action.invoke()
                            }
                        }.addOnFailureListener {
                            Log.w("Error", "SaveToFirebase:  Error updating doc", it)
                        }
                }
        }
        loading = false
    }
}