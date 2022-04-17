package com.skdevelopment.wallpaperapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.skdevelopment.wallpaperapp.Adapters.WallpaperAdapter
import com.skdevelopment.wallpaperapp.Views.Wallpapers

class WallpaperActivity : AppCompatActivity(),
    com.skdevelopment.wallpaperapp.Adapters.itemClicked {

    private lateinit var mAdapter: WallpaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper_list)

        val title = intent.getStringExtra("title")

        getData(title.toString())

        val wallpapersList = findViewById<RecyclerView>(R.id.my_wallpaper_list)
        wallpapersList.layoutManager = GridLayoutManager(this,2)

        mAdapter = WallpaperAdapter(this)
        wallpapersList.adapter = mAdapter

    }


    private fun getData(title: String = "Anime&Superheroes?") {
        val url = "https://api.airtable.com/v0/appBgqfclzAfM8vW4/${title}?api_key=keyrUYrk94L4hPTpF"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonArray = response.getJSONArray("records")
                val wallpapersArray = ArrayList<Wallpapers>()

                for(i in 0 until jsonArray.length()){
                    val wallpapersJsonObject = jsonArray.getJSONObject(i)
                    val wallpaper = Wallpapers(
                        wallpapersJsonObject.getString("id"),
                        wallpapersJsonObject.getJSONObject("fields").getString("URL")
                    )
                    wallpapersArray.add(wallpaper)
                }

                mAdapter.updateWallpapers(wallpapersArray)
            },
            { error ->
                Log.d("myLog", error.toString())
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }



    override fun onItemClicked(item: Wallpapers) {
        val intent = Intent(this, ShowWallpaperActivity::class.java)
        intent.putExtra("imgUrl", item.imgUrl)
        startActivity(intent)
    }
}