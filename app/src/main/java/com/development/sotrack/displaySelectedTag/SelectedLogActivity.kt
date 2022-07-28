package com.development.sotrack.displaySelectedTag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.development.sotrack.R
import com.development.sotrack.log.Log
import com.development.sotrack.log.LogListHolder
import java.util.*

class SelectedLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_log_display)
        setLogViewDetails(UUID.fromString(intent.getStringExtra("selectedUUID")))
    }

    private fun setLogViewDetails(selectedUUID: UUID) {
        val selectedLog: Log = LogListHolder.logList.list.filter { it.id == selectedUUID }[0]
        val buttonImageView: ImageView = this.findViewById(R.id.SLD_pressed_button)
        val dateTextView: TextView = this.findViewById(R.id.SLD_log_date)
        val timeTextView: TextView = this.findViewById(R.id.SLD_log_time)
        dateTextView.text = selectedLog.exactTime.toInstant().toString().slice(0..9)
        timeTextView.text = selectedLog.exactTime.toInstant().toString().slice(11..19)
        if (selectedLog.buttonValue == R.drawable.ic_thumb_down_foreground) {
            buttonImageView.setImageResource(R.drawable.ic_thumb_down_foreground)
            buttonImageView.rotation = 180.0f
        }
    }
}