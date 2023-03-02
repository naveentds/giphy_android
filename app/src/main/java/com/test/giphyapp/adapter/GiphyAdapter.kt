package com.test.giphyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.giphyapp.data.model.Giph
import com.test.giphyapp.databinding.ItemGiphBinding

class GiphyAdapter(private val listener: OnItemClickListener): ListAdapter<Giph, GiphyAdapter.GiphyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val binding = ItemGiphBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return GiphyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class GiphyViewHolder(private val binding: ItemGiphBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val giph = getItem(position)
                        listener.onItemClick(giph)
                    }
                }
            }
        }

        fun bind(giph: Giph){
            binding.apply {
                Glide.with(itemView)
                    .load(giph.images.downsized.url)
                    .apply(RequestOptions()
                        .override(giph.images.downsized.width.toInt(), giph.images.downsized.height.toInt()))
                    .into(ivAGiphImage)
                tvTitle.text = giph.title
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(article: Giph)
    }


    class DiffCallback : DiffUtil.ItemCallback<Giph>(){
        override fun areItemsTheSame(oldItem: Giph, newItem: Giph): Boolean {
            return oldItem.images.downsized.url == newItem.images.downsized.url
        }

        override fun areContentsTheSame(oldItem: Giph, newItem: Giph): Boolean {
            return oldItem == newItem
        }

    }
}