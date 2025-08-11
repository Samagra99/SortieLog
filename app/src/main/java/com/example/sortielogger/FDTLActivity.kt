package com.example.sortielogger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sortielogger.db.AppDatabase
import com.example.sortielogger.util.FDTLCalculator
import kotlinx.android.synthetic.main.activity_fdtl.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FDTLActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fdtl)

        btnCheck.setOnClickListener {
            lifecycleScope.launch {
                val sorties = withContext(Dispatchers.IO) { db.sortieDao().getAll() }
                val results = FDTLCalculator.checkLimitsUsingBlockTime(sorties)
                val sb = StringBuilder()
                results.forEach { (days, pair) ->
                    val (total, max) = pair
                    sb.append("$days days: ${"%.2f".format(total)}h / ${max}h")
                    if (total > max) sb.append("  ⚠️ EXCEEDED")
                    sb.append("\n")
                }
                tvResults.text = sb.toString()
            }
        }
    }
}
