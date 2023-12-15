package com.example.mikiflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mikiflix.databinding.HomePageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response



class HomePage : AppCompatActivity() {
    private lateinit var binding: HomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val apiURL: Map<String, String> = mapOf("monolith" to "https://mikiflix-v2.vercel.app/api/", "micro" to "https://mikiflix-api.vercel.app/meta/anilist/")

        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL["monolith"])
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_anime_card)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val errorMessage = findViewById<TextView>(R.id.error_message)


        binding.account.setOnClickListener {
            val intent = Intent(this, AccountPageActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val intent = Intent(this, InfoAnimeActifity::class.java)

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            try {
                // Sebelum mengambil data, tunjukkan ProgressBar
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE // Sembunyikan pesan kesalahan jika ada
                }

                val animeItems = apiService.getData()

                withContext(Dispatchers.Main) {
                    val adapter = CardAdapter(animeItems.results)
                    adapter.setOnItemClickListener(object : CardAdapter.OnItemClickListener {
                        override fun onItemClick(animeId: String) {
                            intent.putExtra("animeId", animeId)
                            startActivity(intent)
                        }
                    })
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    errorMessage.text = "Error : " + e.message
                    errorMessage.visibility = View.VISIBLE
                }
            } finally {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

}