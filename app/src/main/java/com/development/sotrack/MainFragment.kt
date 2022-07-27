package com.development.sotrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.sotrack.log.LogListHolder
import com.github.mikephil.charting.charts.BarChart

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var tagRecyclerViewAdapter: TagRecyclerViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateBarChart(view)
    }

    override fun onStart() {
        super.onStart()
        recyclerView()
        addData()
    }

    private fun populateBarChart(view: View) {
        val verticalBarChart: BarChart = view.findViewById(R.id.fragment_vertical_barchart)
        val verticalBarChartHandler = VerticalBarChartHandler()
        verticalBarChartHandler.prepareBarChart(verticalBarChart)
    }

    private fun recyclerView() {
        view?.findViewById<RecyclerView>(R.id.tag_list_recycler_view)?.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            val spacingItem = SpacingItem(20)
            addItemDecoration(spacingItem)
            tagRecyclerViewAdapter =
                TagRecyclerViewAdapter { selectedTag: String -> articleClicked(selectedTag) }
            adapter = tagRecyclerViewAdapter
        }
    }

    private fun addData() {
        val data = LogListHolder.logList.list.map { it.app }.distinct()
        tagRecyclerViewAdapter.submitList(data)
    }

    private fun articleClicked(selectedTag: String) {
        startActivity(generateIntent(selectedTag))
    }

    private fun generateIntent(selectedTag: String): Intent {
        return Intent(context, DisplaySelectedTagActivity::class.java).apply {
            putExtra("selectedTag", selectedTag)
        }
    }
}


