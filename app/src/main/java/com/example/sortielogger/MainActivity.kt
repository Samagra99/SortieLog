package com.example.sortielogger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer
import com.example.sortielogger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val utcFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    private lateinit var binding: ActivityMainBinding

    private var clockTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartSortie.setOnClickListener { startActivity(Intent(this, SortieEditActivity::class.java)) }
        binding.btnViewSorties.setOnClickListener { startActivity(Intent(this, SortieListActivity::class.java)) }
        binding.btnFdtl.setOnClickListener { startActivity(Intent(this, FDTLActivity::class.java)) }

        clockTimer = timer(period = 1000) {
            runOnUiThread { binding.tvUtcClock.text = utcFormat.format(Date()) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clockTimer?.cancel()
    }
}