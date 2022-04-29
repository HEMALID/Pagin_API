package com.example.pagin_api


class   MyData : ArrayList<MyData.MyDataItem>(){
    data class MyDataItem(
        val completed: Boolean,
        val id: String,
        val title: String,
        val userId: String
    )
}