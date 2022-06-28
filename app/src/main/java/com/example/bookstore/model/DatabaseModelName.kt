package com.example.bookstore.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// This tells room database compiler and this class will be complied into an entity
// There has to be a primary key in each table so here we use @PrimaryKey to signify that.
@Entity(tableName = "some_table_name")
data class DatabaseModelName(
    // To make it not nullable
    @NonNull
    @PrimaryKey
    @ColumnInfo
    val id:String,
    @ColumnInfo(name = "anyNameWeWant")
    val someOtherColumn: String
)