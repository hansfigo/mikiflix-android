package com.example.mikiflix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CardAdapter(private val cardItems: List<AnimeData>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(animeId: String)
    }

    private var listener: OnItemClickListener? = null

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImg: ImageView = itemView.findViewById(R.id.anime_img)
        val infoText: TextView = itemView.findViewById(R.id.info_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardItems[position]
        val animeImg = holder.animeImg

        // Menggunakan Glide untuk memuat gambar dari URL
        Glide.with(animeImg)
            .load(currentItem.image) // Ganti dengan URL gambar dari objek currentItem
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Opsional: Menggunakan cache
            .into(animeImg)

        holder.infoText.text = currentItem.title.english

        holder.animeImg.setOnClickListener {
            listener?.onItemClick(currentItem.id) // Ganti animeId dengan ID anime yang sesuai di sini
        }
    }

    override fun getItemCount() = cardItems.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
