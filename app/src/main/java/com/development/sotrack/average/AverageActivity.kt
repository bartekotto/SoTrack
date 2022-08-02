package com.development.sotrack.average

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.development.sotrack.R
import com.development.sotrack.VerticalBarChartHandler
import com.github.mikephil.charting.charts.BarChart

class AverageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_average)
        populateBarChart(this)
    }


    private fun populateBarChart(view: AverageActivity) {
        val verticalBarChart: BarChart = view.findViewById(R.id.fragment_vertical_barchart)
        val verticalBarChartHandler = VerticalBarChartHandler()
        verticalBarChartHandler.prepareBarChart(verticalBarChart, 0)
    }

    fun applyHourClickListener() {

    }
}