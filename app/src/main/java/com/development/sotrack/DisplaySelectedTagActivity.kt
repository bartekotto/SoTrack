package com.development.sotrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DisplaySelectedTagActivity : AppCompatActivity() {
    private var selectedTag: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_tag_display)
        selectedTag = intent.getStringExtra("selectedTag")
    }
}