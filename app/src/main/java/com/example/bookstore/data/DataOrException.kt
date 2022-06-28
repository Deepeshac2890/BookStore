package com.example.composestarterproject.data

import java.lang.Exception

// To add more meta data to the data we receive
// This is a generic class which can have anyDataType in it.
class DataOrException<T,Boolean,E:Exception> (
    var data: T? = null,
    var loading: Boolean? = null,
    var e:E? = null
)