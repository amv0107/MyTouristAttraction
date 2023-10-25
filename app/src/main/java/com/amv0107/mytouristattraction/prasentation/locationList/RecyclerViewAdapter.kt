package com.amv0107.mytouristattraction.prasentation.locationList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.amv0107.mytouristattraction.R
import com.amv0107.mytouristattraction.data.Places
import com.amv0107.mytouristattraction.databinding.PlaceItemBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val items: MutableList<Places>) : Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = PlaceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val binding = holder.binding
        binding.placeName.text = items[position].name
        if (items[position].photos.isNullOrEmpty()) {
            binding.placeImage.setImageDrawable(binding.placeImage.context.getDrawable(R.drawable.ic_not_image))
        } else {
            Picasso.get()
                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photo_reference=${items[position].photos[0].photoReference}&key=AIzaSyDlmthMDpc7f1D2vOxJdWif_7Hfc86Ks24")
                .into(binding.placeImage)
        }
    }

    override fun getItemCount(): Int = items.size
}

class RecyclerViewHolder(val binding: PlaceItemBinding) : RecyclerView.ViewHolder(binding.root)