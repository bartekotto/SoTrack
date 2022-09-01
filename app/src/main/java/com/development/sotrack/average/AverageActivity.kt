package com.development.sotrack.average

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.development.sotrack.R
import com.development.sotrack.VerticalBarChartHandler
import com.github.mikephil.charting.charts.BarChart

class AverageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_average)
        populateBarChart()
        findViewById<TextView>(R.id.weekly_tv).setOnClickListener { applyClickListener(0) }
        findViewById<TextView>(R.id.hourly_tv).setOnClickListener { applyClickListener(1) }
        findViewById<TextView>(R.id.yearly_tv).setOnClickListener { applyClickListener(2) }
    }


    private fun populateBarChart() {
        val verticalBarChart: BarChart = findViewById(R.id.fragment_vertical_barchart)
        val verticalBarChartHandler = VerticalBarChartHandler()
        verticalBarChartHandler.prepareBarChart(verticalBarChart, 0)
    }

    private fun applyClickListener(format : Int) {
        val verticalBarChart: BarChart = findViewById(R.id.fragment_vertical_barchart)
        val verticalBarChartHandler = VerticalBarChartHandler()
        verticalBarChartHandler.prepareBarChart(verticalBarChart, format)
        verticalBarChart.notifyDataSetChanged()
        verticalBarChart.invalidate()
    }
}