package com.example.pagin_api

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagin_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    lateinit var ulist: ArrayList<MyData.MyDataItem>
    private var isLoading = false

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        getMyData()

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount: Int = linearLayoutManager.childCount
                val totleItemCount: Int = linearLayoutManager.itemCount
                val firstVisibleItemPosition: Int =
                    linearLayoutManager.findFirstVisibleItemPosition()

                if (isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totleItemCount && firstVisibleItemPosition >= 0) {
                        getMyData()
                    }
                }

            }
        })

    }

    private fun setAdapter() {
        ulist = arrayListOf()
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = linearLayoutManager
        myAdapter = MyAdapter(baseContext, ulist)
        binding.recyclerview.adapter = myAdapter
    }

    private fun getMyData() {

        isLoading = true
        binding.pbar.visibility = View.VISIBLE
        binding.pbar.progressTintList = ColorStateList.valueOf(Color.RED)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyData.MyDataItem>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<MyData.MyDataItem>?>,
                response: Response<List<MyData.MyDataItem>?>
            ) {
                val responseBody = response.body()!!
                ulist.addAll(responseBody)

                binding.pbar.postDelayed(Runnable {
                    myAdapter.notifyDataSetChanged()
                    isLoading = false
                    binding.pbar.visibility = View.INVISIBLE
                }, 3000)

            }

            override fun onFailure(call: Call<List<MyData.MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity", "onfailer" + t.message)
            }
        })

    }

}