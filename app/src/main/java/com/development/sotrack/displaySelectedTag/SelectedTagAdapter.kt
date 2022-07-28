package com.development.sotrack.displaySelectedTag

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.development.sotrack.R
import com.development.sotrack.log.Log
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class SelectedTagAdapter constructor(private val clickListener: (UUID) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var filteredLogs: List<Log> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.log_row_item,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return filteredLogs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagViewHolder -> {
                holder.bind(filteredLogs[position], clickListener)
            }
        }
    }

    fun submitList(tagsList: List<Log>) {
        filteredLogs = tagsList
    }

}

class TagViewHolder constructor(
    private val logView: View
) : RecyclerView.ViewHolder(logView) {
    private val logTimeTextView: TextView = logView.findViewById(R.id.time_textview)
    private val logDateTextView: TextView = logView.findViewById(R.id.date_textview)
    private val pressedButton: AppCompatImageView = logView.findViewById(R.id.pressed_button)
    fun bind(log: Log, clickListener: (UUID) -> Unit) {
        logDateTextView.text = log.exactTime.toInstant().toString().slice(0..9)
        logTimeTextView.text = log.exactTime.toInstant().toString().slice(11..19)
        if (log.buttonValue == R.drawable.ic_thumb_down_foreground) {
            pressedButton.setImageResource(R.drawable.ic_thumb_down_foreground)
            pressedButton.rotation = 180.0f
        }
        logView.setOnClickListener { clickListener(log.id) }
    }


}