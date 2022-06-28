package com.example.bookstore.di

import com.example.bookstore.network.BooksAPI
import com.example.bookstore.repository.BooksRepository
import com.example.bookstore.repository.FireRepository
import com.example.bookstore.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideBooksApi(): BooksAPI {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BooksAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideBooksRepository(api: BooksAPI): BooksRepository {
        return BooksRepository(api = api)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository() =
        FireRepository(queryBook = FirebaseFirestore.getInstance().collection("books"))
}