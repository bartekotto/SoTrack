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
import kotlin.random.Random


class VerticalBarChartHandler {
    fun prepareBarChart(chart: BarChart, dataFormat: Int = 3): BarChart {
        populateLogList(100, LogListHolder.logList)
        configureChartAppearance(chart)
        if (dataFormat == 3) {
            prepareChartData(
                listOf("M", "T", "W", "T", "F", "S", "S", "M", "T", "W"),
                chart,
                LogListHolder.logList
            )
        } else {
            prepareChartDataAverage(dataFormat, chart, LogListHolder.logList)
        }
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
        chart.legend.isEnabled = false
        chart.setDrawBorders(false)
        return chart
    }


    private fun prepareChartData(
        labels: List<String>,
        chart: BarChart,
        logList: LogList
    ): BarChart {
        val values: ArrayList<BarEntry> = ArrayList()
        for (i in labels.indices) {
            val thumbsUp: Float = logList.list.filter { it.date == Calendar.DAY_OF_YEAR - i }
                .filter { it.buttonValue == 1 }.size.toFloat()
            val thumbsDown: Float = logList.list.filter { it.date == Calendar.DAY_OF_YEAR - i }
                .filter { it.buttonValue == 0 }.size.toFloat()
            values.add(BarEntry(i.toFloat(), floatArrayOf(thumbsUp, thumbsDown)))
        }
        Collections.rotate(labels, Calendar.DAY_OF_WEEK)
        val barDataSet = BarDataSet(values, "")
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        finalizeDataSets(dataSets, barDataSet, labels, chart)
        return chart
    }

    private fun prepareChartDataAverage(scale: Int, chart: BarChart, logList: LogList): BarChart {
        var values: ArrayList<BarEntry> = ArrayList()
        var labels = listOf<String>()
        when (scale) {
            0 -> {
                labels = listOf("M", "T", "W", "T", "F", "S", "S")
                values =
                    extractAverages(labels, logList, values, Calendar.DAY_OF_WEEK)
            }
            1 -> {
                labels = listOf(
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10",
                    "11",
                    "12",
                    "13",
                    "14",
                    "15",
                    "16",
                    "17",
                    "18",
                    "19",
                    "20",
                    "21",
                    "22",
                    "23",
                    "24"
                )
                values =
                    extractAverages(labels, logList, values, Calendar.HOUR_OF_DAY)
            }

            2 -> {
                labels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
                values =
                    extractAverages(labels, logList, values, Calendar.MONTH)
            }
        }
        val barDataSet = BarDataSet(values, "")
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        finalizeDataSets(dataSets, barDataSet, labels, chart)
        return chart
    }

    private fun finalizeDataSets(
        dataSets: ArrayList<IBarDataSet>,
        barDataSet: BarDataSet,
        labels: List<String>,
        chart: BarChart
    ) {
        dataSets.add(barDataSet)
        barDataSet.colors = listOf(ColorTemplate.PASTEL_COLORS[1], ColorTemplate.PASTEL_COLORS[3])
        barDataSet.stackLabels = labels.toTypedArray()
        chart.data = BarData(dataSets)
        chart.data.barWidth = 0.5f
        chart.invalidate()
    }

    private fun extractAverages(
        labels: List<String>,
        logList: LogList,
        averages: ArrayList<BarEntry>,
        timeFrame: Int
    ): ArrayList<BarEntry> {
        for (i in labels.indices) {
            averages += BarEntry(
                i.toFloat(),
                logList.list.filter { it.exactTime.get(timeFrame) == i + 1 && it.buttonValue == 1 }.size.toFloat(),
                logList.list.filter { it.exactTime.get(timeFrame) == i + 1 && it.buttonValue == 0 }.size.toFloat()
            )
        }
        return averages
    }

    private fun populateLogList(amount: Int, logList: LogList) {
        val cal: Calendar = Calendar.getInstance()
        for (i in 1..amount) {
            cal.time =
                (Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000) * Random.nextInt(11)))
            cal.set(Calendar.DAY_OF_WEEK, (1..7).random())
            cal.set(Calendar.HOUR_OF_DAY, (1..24).random())
            logList.list += Log(
                UUID.randomUUID(),
                Calendar.DAY_OF_YEAR - Random.nextInt(11),
                cal,
                randomApp(),
                randomThumb()
            )
        }
    }


    private fun randomApp(): String {
        val list = listOf("instagram", "twitter", "reddit", "tiktok")
        val randomIndex = Random.nextInt(list.size);
        return list[randomIndex]
    }

    private fun randomThumb(): Int {
        val list = listOf(0, 1)
        val randomIndex = Random.nextInt(list.size);
        return list[randomIndex]
    }
//
//    companion object {
//        private const val MAX_X_VALUE = 7
//        private const val MAX_Y_VALUE = 50
//        private const val MIN_Y_VALUE = 5
//    }
}