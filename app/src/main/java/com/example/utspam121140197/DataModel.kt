package com.example.utspam121140197

data class DataModel (val data: ArrayList<Data>) {
    data class Data (val id: Int, val email: String, val first_name: String, val last_name: String, val avatar: String)
}