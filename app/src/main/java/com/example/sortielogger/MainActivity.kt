package com.example.sortielogger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private val utcFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    private var clockTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNewSortie.setOnClickListener { startActivity(Intent(this, SortieEditActivity::class.java)) }
        btnOpenList.setOnClickListener { startActivity(Intent(this, SortieListActivity::class.java)) }
        btnFdtl.setOnClickListener { startActivity(Intent(this, FDTLActivity::class.java)) }

        clockTimer = timer(period = 1000) {
            runOnUiThread { tvUtcClock.text = utcFormat.format(Date()) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clockTimer?.cancel()
    }
}
