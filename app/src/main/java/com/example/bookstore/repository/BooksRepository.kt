package com.example.bookstore.repository

import com.example.composestarterproject.data.DataOrException
import com.example.bookstore.model.Item
import com.example.bookstore.network.BooksAPI
import java.lang.Exception
import javax.inject.Inject

class BooksRepository @Inject constructor(private val api: BooksAPI) {

    suspend fun getAllBooks(q: String) : DataOrException<List<Item>, Boolean, Exception> {
        val response = try{
            api.getAllBooks(q)
        }
        catch (e:Exception) {
            return DataOrException(e=e)
        }
        return DataOrException(data = response.items)
    }

    suspend fun getParticularBook(bookId: String) : DataOrException<Item, Boolean, Exception> {
        val response = try{
            api.getParticularBook(bookId)
        }
        catch (e:Exception) {
            return DataOrException(e=e)
        }
        return DataOrException(data = response)
    }
}