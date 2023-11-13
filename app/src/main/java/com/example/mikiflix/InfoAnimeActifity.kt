package com.example.mikiflix

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mikiflix.databinding.InfoAnimeActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InfoAnimeActifity : AppCompatActivity() {
    private lateinit var binding: InfoAnimeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InfoAnimeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        val animeId = intent.getStringExtra("animeId")

        val title = findViewById<TextView>(R.id.anime_title)
        val desc = findViewById<TextView>(R.id.anime_desc)
        val backBtn = findViewById<ImageView>(R.id.ic_back)

        backBtn.setOnClickListener{
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mikiflix-api.vercel.app/meta/anilist/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {
            try {
                val animeInfoData = apiService.getAnimeInfo(animeId!!)
                val imgCoverUrl = animeInfoData.image
                val bgImageUlr = animeInfoData.cover
                val animeTitle = animeInfoData.title?.english
                val animeDesc = animeInfoData.description

                withContext(Dispatchers.Main){
                    val coverImageView : ImageView = findViewById(R.id.cover_image)
                    val bgImageView : ImageView = findViewById(R.id.bg_image)
                    Glide.with(coverImageView)
                        .load(imgCoverUrl) // Ganti dengan URL gambar dari objek currentItem
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Opsional: Menggunakan cache
                        .into(coverImageView)

                    Glide.with(bgImageView)
                        .load(bgImageUlr) // Ganti dengan URL gambar dari objek currentItem
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Opsional: Menggunakan cache
                        .into(bgImageView)


                    title.text = animeTitle
                        desc.text= animeDesc

                }
            }catch (e : Exception){
                println(animeId)
                e.printStackTrace() // Tampilkan pesan kesalahan
            }
        }


    }
}