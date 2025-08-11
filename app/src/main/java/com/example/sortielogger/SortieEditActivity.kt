package com.example.sortielogger

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sortielogger.db.AppDatabase
import com.example.sortielogger.model.Sortie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import com.example.sortielogger.databinding.ActivitySortieEditBinding
import java.util.*

class SortieEditActivity : AppCompatActivity() {
    private val fmtTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val fmtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val db by lazy { AppDatabase.get(this) }
    private lateinit var binding: ActivitySortieEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySortieEditBinding.inflate(layoutInflater)

        binding.tvUtcDate.text = fmtDate.format(Date())

        binding.btnSaveSortie.setOnClickListener { binding.etOffBlock.setText(fmtTime.format(Date())) }
        binding.btnSaveSortie.setOnClickListener { binding.etAirborne.setText(fmtTime.format(Date())) }
        binding.btnSaveSortie.setOnClickListener { binding.etTouchdown.setText(fmtTime.format(Date())) }
        binding.btnSaveSortie.setOnClickListener { binding.etOnBlock.setText(fmtTime.format(Date())) }

        // manual time picker: tap EditText to open TimePicker (UTC)
        listOf(binding.etOffBlock, binding.etAirborne, binding.etTouchdown, binding.etOnBlock).forEach { et ->
            et.setOnClickListener {
                val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                TimePickerDialog(this, { _, h, m ->
                    val s = String.format(Locale.getDefault(), "%02d:%02d:00", h, m)
                    et.setText(s)
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }
        }

        binding.btnSaveSortie.setOnClickListener {
            val sortie = Sortie(
                date = Date(),
                aircraftReg = binding.etAircraft.text.toString(),
                offBlock = binding.etOffBlock.text.toString(),
                airborne = binding.etAirborne.text.toString(),
                touchdown = binding.etTouchdown.text.toString(),
                onBlock = binding.etOnBlock.text.toString(),
                remarks = binding.etRemarks.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                db.sortieDao().insert(sortie)
                runOnUiThread { finish() }
            }
        }
    }
}
