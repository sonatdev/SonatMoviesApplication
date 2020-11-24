package com.sonat.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.main_activity_text_view).apply {
            setOnClickListener { openMovieDetails() }
        }
    }

    private fun openMovieDetails() {
        startActivity(Intent(this, MovieDetailsActivity::class.java))
    }
}