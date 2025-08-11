package com.example.sortielogger

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sortielogger.db.AppDatabase
import com.example.sortielogger.model.Sortie
import kotlinx.android.synthetic.main.activity_sortie_edit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SortieEditActivity : AppCompatActivity() {
    private val fmtTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val fmtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sortie_edit)

        tvUtcDate.text = fmtDate.format(Date())

        btnStart.setOnClickListener { etOffBlock.setText(fmtTime.format(Date())) }
        btnAirborne.setOnClickListener { etAirborne.setText(fmtTime.format(Date())) }
        btnTouchdown.setOnClickListener { etTouchdown.setText(fmtTime.format(Date())) }
        btnStop.setOnClickListener { etOnBlock.setText(fmtTime.format(Date())) }

        // manual time picker: tap EditText to open TimePicker (UTC)
        listOf(etOffBlock, etAirborne, etTouchdown, etOnBlock).forEach { et ->
            et.setOnClickListener {
                val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                TimePickerDialog(this, { _, h, m ->
                    val s = String.format(Locale.getDefault(), "%02d:%02d:00", h, m)
                    et.setText(s)
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }
        }

        btnSave.setOnClickListener {
            val sortie = Sortie(
                date = Date(),
                aircraftReg = etAircraft.text.toString(),
                offBlock = etOffBlock.text.toString(),
                airborne = etAirborne.text.toString(),
                touchdown = etTouchdown.text.toString(),
                onBlock = etOnBlock.text.toString(),
                remarks = etRemarks.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                db.sortieDao().insert(sortie)
                runOnUiThread { finish() }
            }
        }
    }
}
