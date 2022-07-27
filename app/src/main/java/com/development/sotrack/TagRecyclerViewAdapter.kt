package com.development.sotrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TagRecyclerViewAdapter constructor(private val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var tags: List<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.text_row_item,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagViewHolder -> {
                holder.bind(tags[position], clickListener)
            }
        }
    }

    fun submitList(tagsList: List<String>) {
        tags = tagsList
    }

}

class TagViewHolder constructor(
    tagView: View
) : RecyclerView.ViewHolder(tagView) {

    private val tagTextView: TextView = tagView.findViewById(R.id.tagTextView)
    fun bind(tag: String, clickListener: (String) -> Unit) {
        tagTextView.text = tag
        tagTextView.setOnClickListener { clickListener(tag) }
    }


}