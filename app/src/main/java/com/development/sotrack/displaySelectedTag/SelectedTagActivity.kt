package com.development.sotrack.displaySelectedTag

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.sotrack.R
import com.development.sotrack.log.LogListHolder
import java.util.*

class SelectedTagActivity : AppCompatActivity() {
    private lateinit var selectedTagAdapter: SelectedTagAdapter

    private lateinit var arrow: ImageButton
    private var selectedTag: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_tag_display)
        selectedTag = intent.getStringExtra("selectedTag")
        setSelectedTagTextView()
        arrow = findViewById(R.id.arrow_button)
        arrow.setOnClickListener { arrowOnClickListener(findViewById(R.id.expandable_view)) }
        findViewById<Button>(R.id.filter_button).setOnClickListener { filterOnClickListener() }
    }

    override fun onStart() {
        super.onStart()
        recyclerView()
        addData()
    }


    private fun arrowOnClickListener(hiddenView: View) {
        if (hiddenView.visibility == View.GONE) {
            TransitionManager.beginDelayedTransition(
                findViewById(R.id.selected_tag_layout),
                AutoTransition()
            )
            hiddenView.visibility = View.VISIBLE
            arrow.rotation = 180.0f
        } else {
            hiddenView.visibility = View.GONE
            arrow.rotation = 0.0f
            TransitionManager.beginDelayedTransition(
                findViewById(R.id.selected_tag_layout),
                AutoTransition()
            )
        }
    }

    private fun filterOnClickListener() {
        val checkedInteractions: MutableList<Int> = extractCheckboxes()
        val dateRange: MutableList<Date> = extractDates()
        addFilteredData(dateRange, checkedInteractions)
    }

    private fun extractCheckboxes(): MutableList<Int> {
        val checkedInteractions: MutableList<Int> = mutableListOf()
        if (findViewById<CheckBox>(R.id.thumb_up_checkBox).isChecked)
            checkedInteractions += R.drawable.ic_thumb_up_foreground
        else if (findViewById<CheckBox>(R.id.thumb_down_checkBox).isChecked)
            checkedInteractions += R.drawable.ic_thumb_down_foreground
        return checkedInteractions
    }

    private fun extractDates(): MutableList<Date> {
        val dates: MutableList<Date> = mutableListOf()
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY)
        dates += df.parse(
            findViewById<EditText>(R.id.editTextDateFrom).text.toString()
        )
        dates += df.parse(
            findViewById<EditText>(R.id.editTextDateTo).text.toString()
        )

        return dates
        TODO("fix empty feild null")
    }

    private fun setSelectedTagTextView() {
        val selectedTagTextView: TextView = findViewById(R.id.displayed_tag)
        selectedTagTextView.text = selectedTag
    }


    private fun recyclerView() {
        this.findViewById<RecyclerView>(R.id.filtered_log_recyclerview)?.apply {
            layoutManager = LinearLayoutManager(context)
            val spacingItem = SpacingItem(20)
            addItemDecoration(spacingItem)
            selectedTagAdapter =
                SelectedTagAdapter { selectedUUID: UUID -> logClicked(selectedUUID) }
            adapter = selectedTagAdapter
        }
    }


    private fun addData() {
        val data = LogListHolder.logList.list.filter {
            it.tags == selectedTag
        }
        selectedTagAdapter.submitList(data)
    }

    private fun addFilteredData(
        dateRange: MutableList<Date>,
        checkedInteractions: MutableList<Int>
    ) {
        var data = LogListHolder.logList.list.filter {
            it.tags == selectedTag
        }
        data = data.filter { checkedInteractions.contains(it.buttonValue) }
        data = data.filter { it.exactTime.before(dateRange[1]) && it.exactTime.after(dateRange[0]) }
        selectedTagAdapter.submitList(data)
        TODO("fix the buttons!")
    }

    private fun logClicked(selectedLog: UUID) {
        startActivity(generateIntent(selectedLog))
    }

    private fun generateIntent(selectedUUID: UUID): Intent {
        return Intent(this, SelectedLogActivity::class.java).apply {
            putExtra("selectedUUID", selectedUUID.toString())
        }
    }
}