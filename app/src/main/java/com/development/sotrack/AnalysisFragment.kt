package com.development.sotrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.sotrack.R
import com.development.sotrack.displaySelectedTag.SelectedLogActivity
import com.development.sotrack.displaySelectedTag.SelectedTagAdapter
import com.development.sotrack.displaySelectedTag.SpacingItem
import com.development.sotrack.notifications.Discovery
import com.development.sotrack.notifications.DiscoveryListHolder
import com.github.mikephil.charting.charts.BarChart
import java.util.*

class AnalysisFragment : Fragment(R.layout.fragment_analysis) {
    private lateinit var analysisDiscoveryAdapter: AnalysisDiscoveryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateList()
        recyclerView()
        populateBarChart(view)

    }


    private fun recyclerView() {
        view?.findViewById<RecyclerView>(R.id.discovery_rv)?.apply {
            layoutManager = LinearLayoutManager(context)
            val spacingItem = SpacingItem(20)
            addItemDecoration(spacingItem)
            analysisDiscoveryAdapter =
                AnalysisDiscoveryAdapter { selectedID: Int -> discoveryClicked(selectedID) }
            adapter = analysisDiscoveryAdapter
        }
    }

    private fun discoveryClicked(selectedID: Int) {
        startActivity(generateIntent(selectedID))
    }

    private fun generateIntent(selectedID: Int): Intent {
        return Intent(context, SelectedLogActivity::class.java).apply {
            putExtra("selectedUUID", selectedID.toString())
        }
    }

    private fun populateBarChart(view: View) {
        val verticalBarChart: BarChart = view.findViewById(R.id.fragment_vertical_barchart)
        val verticalBarChartHandler = VerticalBarChartHandler()
        verticalBarChartHandler.prepareBarChart(verticalBarChart)
    }

    fun populateList() {
        val discovery1: Discovery = Discovery(
            1,
            "Trend Discovered",
            Date(System.currentTimeMillis()),
            "Trend",
            R.mipmap.ic_trend_foreground
        )
        val discovery2: Discovery = Discovery(
            2,
            "Surge Discovered",
            Date(System.currentTimeMillis()),
            "Trend",
            R.mipmap.ic_surge_foreground
        )
        val discovery3: Discovery = Discovery(
            3,
            "Pattern Discovered",
            Date(System.currentTimeMillis()),
            "Trend",
            R.mipmap.ic_trend_foreground
        )
        val discovery4: Discovery = Discovery(
            4,
            "Trend Discovered",
            Date(System.currentTimeMillis()),
            "Trend",
            R.mipmap.ic_trend_foreground
        )
        val discovery5: Discovery = Discovery(
            5,
            "Pattern Discovered",
            Date(System.currentTimeMillis()),
            "Trend",
            R.mipmap.ic_pattern_foreground
        )
        DiscoveryListHolder.discoveryList.discoveryList += discovery1
        DiscoveryListHolder.discoveryList.discoveryList += discovery2
        DiscoveryListHolder.discoveryList.discoveryList += discovery3
        DiscoveryListHolder.discoveryList.discoveryList += discovery4
        DiscoveryListHolder.discoveryList.discoveryList += discovery5
    }
}
