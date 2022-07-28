package com.development.sotrack

import com.development.sotrack.log.Log
import com.development.sotrack.log.LogList
import com.development.sotrack.log.LogListHolder
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class VerticalBarChartHandler {
    fun prepareBarChart(chart: BarChart): BarChart {
//        val data = createChartData()
        if (LogListHolder.logList.list.isEmpty()) {
            populateLogList(100, LogListHolder.logList)
        }
        configureChartAppearance(chart)
        prepareChartData(chart, LogListHolder.logList)
        return chart
    }

    private fun configureChartAppearance(chart: BarChart): BarChart {
        chart.description.isEnabled = false
        chart.setDrawValueAboveBar(false)
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        val axisLeft = chart.axisLeft
        axisLeft.setDrawGridLines(false)
        axisLeft.granularity = 10f
        axisLeft.axisMinimum = 0f
        val axisRight = chart.axisRight
        axisRight.setDrawGridLines(false)
        axisRight.granularity = 10f
        axisRight.axisMinimum = 0f
        chart.setDrawBorders(false)
        return chart
    }


    private fun prepareChartData(chart: BarChart, logList: LogList): BarChart {
        val values: ArrayList<BarEntry> = ArrayList()
        val labels = listOf("M", "T", "W", "T", "F", "S", "S", "M", "T", "W")
        for (i in 0..9) {
            val thumbsUp: Float = logList.list.filter { it.date == Calendar.DAY_OF_YEAR - i }
                .filter { it.buttonValue == R.drawable.ic_thumb_down_foreground }.size.toFloat()
            val thumbsDown: Float = logList.list.filter { it.date == Calendar.DAY_OF_YEAR - i }
                .filter { it.buttonValue == R.drawable.ic_thumb_up_foreground }.size.toFloat()
            values.add(BarEntry(i.toFloat(), floatArrayOf(thumbsUp, thumbsDown)))
        }
        Collections.rotate(labels, Calendar.DAY_OF_WEEK)
        val barDataSet = BarDataSet(values, "whatevs")
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(barDataSet)
        barDataSet.colors = listOf(ColorTemplate.PASTEL_COLORS[1], ColorTemplate.PASTEL_COLORS[3])
        barDataSet.stackLabels = labels.toTypedArray()
        chart.data = BarData(dataSets)
        chart.data.barWidth = 0.5f
        chart.invalidate()
        return chart
    }

    private fun populateLogList(amount: Int, logList: LogList) {
        for (i in 1..amount)
            logList.list += Log(
                UUID.randomUUID(),
                "temporary",
                Calendar.DAY_OF_YEAR - Random.nextInt(11),
                Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000) * Random.nextInt(11)),
                randomApp(),
                arrayOf(),
                randomThumb()
            )
    }


    private fun randomApp(): String {
        val list = listOf("instagram", "twitter", "reddit", "tiktok")
        val randomIndex = Random.nextInt(list.size);
        return list[randomIndex]
    }

    private fun randomThumb(): Int {
        val list = listOf(R.drawable.ic_thumb_down_foreground, R.drawable.ic_thumb_up_foreground)
        val randomIndex = Random.nextInt(list.size);
        return list[randomIndex]
    }

    companion object {
        private const val MAX_X_VALUE = 7
        private const val MAX_Y_VALUE = 50
        private const val MIN_Y_VALUE = 5
    }
}