package com.development.sotrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.development.sotrack.notifications.Discovery
import com.development.sotrack.notifications.DiscoveryListHolder
import java.util.*

class AnalysisDiscoveryAdapter constructor(private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var discoveryList: List<Discovery> = DiscoveryListHolder.discoveryList.discoveryList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiscoveryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.analysis_row_item,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return discoveryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DiscoveryViewHolder -> {
                holder.bind(discoveryList[position], clickListener)
            }
        }
    }


}

class DiscoveryViewHolder constructor(
    private val discoveryView: View
) : RecyclerView.ViewHolder(discoveryView) {
    private val discoveryTimeTextView: TextView = discoveryView.findViewById(R.id.discovery_time_tv)
    private val discoveryMessageTextView: TextView =
        discoveryView.findViewById(R.id.discovery_message_tv)
    private val discoveryIcon: AppCompatImageView = discoveryView.findViewById(R.id.discovery_icon)
    fun bind(discovery: Discovery, clickListener: (Int) -> Unit) {
        discoveryMessageTextView.text = discovery.message
        discoveryTimeTextView.text = discovery.time.toInstant().toString().slice(11..19)
        discoveryIcon.setImageResource(discovery.icon)

        discoveryView.setOnClickListener { clickListener(discovery.id) }
    }

}