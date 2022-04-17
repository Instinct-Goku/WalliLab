package com.skdevelopment.wallpaperapp


import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*


class ShowWallpaperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_wallpaper)

        val imgUrl = intent.getStringExtra("imgUrl")
        val imgView = findViewById<ImageView>(R.id.wallpaper_img)
        Glide.with(this).load(imgUrl).into(imgView)

        val mainFab = findViewById<FloatingActionButton>(R.id.main_fab)
        val shareFab = findViewById<FloatingActionButton>(R.id.share_fab)
        val downloadFab = findViewById<FloatingActionButton>(R.id.down_fab)
        val setFab = findViewById<FloatingActionButton>(R.id.set_fab)

        mainFab.setOnClickListener {
            if(shareFab.isVisible){
                shareFab.visibility = View.GONE
                downloadFab.visibility = View.GONE
                setFab.visibility = View.GONE

            }
            else{
                shareFab.visibility = View.VISIBLE
                downloadFab.visibility = View.VISIBLE
                setFab.visibility = View.VISIBLE
            }
        }

        shareFab.setOnClickListener {
            shareWallpaper()
        }

        downloadFab.setOnClickListener {
            downloadWallaper(this)
        }

        setFab.setOnClickListener {
            setWallpaper()
        }
    }

    private fun setWallpaper() {
        val imageview = findViewById<ImageView>(R.id.wallpaper_img)
        val image: Bitmap?= getImageBitmap(imageview)
        val wallpaperManager = WallpaperManager.getInstance(baseContext)

        wallpaperManager.setBitmap(image)

        Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show()
    }


    private fun downloadWallaper(context: Context) {
        val imageview = findViewById<ImageView>(R.id.wallpaper_img)
        val image: Bitmap?= getImageBitmap(imageview)
        val imgUri = getImageURI(this, image!!)
        Log.v("IMG Dir", imgUri.toString())
        Toast.makeText(this, "wallpaper saved at: ${imgUri.toString()}", Toast.LENGTH_LONG).show()
    }

    private fun shareWallpaper() {
        val imageview = findViewById<ImageView>(R.id.wallpaper_img)
        val image: Bitmap?= getImageBitmap(imageview) //converting current image to bitmap

        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/*"
        share.putExtra(Intent.EXTRA_STREAM, getImageURI(this, image!!))
        startActivity(Intent.createChooser(share, "Share Via..."))
    }

    private fun getImageBitmap(view: ImageView) : Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    //returns image URI from image bitmap
    private fun getImageURI(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Wallpaper_${(99..9999).random()}", null)
        return Uri.parse(path)
    }
}