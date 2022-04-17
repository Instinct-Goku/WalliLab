package com.skdevelopment.wallpaperapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.skdevelopment.wallpaperapp.Views.Category

class MainActivity : AppCompatActivity(), itemClicked {

    private lateinit var mAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catList = findViewById<RecyclerView>(R.id.category_list)

        getData()
        mAdapter = CategoryAdapter(this)
        catList.adapter = mAdapter

    }

    private fun getData() {
        val url = "https://api.airtable.com/v0/app3SpF1pKZgel0U1/Categories?api_key=keyrUYrk94L4hPTpF"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonArray = response.getJSONArray("records")
                val categoryArray = ArrayList<Category>()

                for(i in 0 until jsonArray.length()){
                    val categoryJsonObject = jsonArray.getJSONObject(i)
                    val category = Category(
                        categoryJsonObject.getString("id"),
                        categoryJsonObject.getJSONObject("fields").getString("Name"),
                        categoryJsonObject.getJSONObject("fields").getString("URL")
                    )
                    categoryArray.add(category)
                }

                mAdapter.updateCategory(categoryArray)
            },
            { error ->
                Log.d("myLog", error.toString())
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: Category) {
        val intent = Intent(this, WallpaperActivity::class.java)
        intent.putExtra("title", item.title)
        startActivity(intent)
    }
}