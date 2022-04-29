package com.example.pagin_api

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pagin_api.databinding.RowItemBinding

class MyAdapter(var context:Context,val userList:List<MyData.MyDataItem>):RecyclerView.Adapter<MyAdapter.ViewHolder> (){

    class ViewHolder(val binding: RowItemBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(item: MyData.MyDataItem){
           binding.data = item
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}