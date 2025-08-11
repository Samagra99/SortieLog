package com.example.sortielogger

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sortielogger.db.AppDatabase
import com.example.sortielogger.util.CsvExporter
import kotlinx.android.synthetic.main.activity_sortie_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class SortieListActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.get(this) }
    private val dateFmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sortie_list)

        btnRefresh.setOnClickListener { loadList() }
        btnExport.setOnClickListener {
            lifecycleScope.launch {
                val sorties = withContext(Dispatchers.IO) { db.sortieDao().getAll() }
                val file = withContext(Dispatchers.IO) { CsvExporter.exportSorties(this@SortieListActivity, sorties) }
                runOnUiThread { toast("CSV exported: ${file.absolutePath}") }
            }
        }

        lvSorties.setOnItemClickListener { _, _, position, _ ->
            // For now open editor (edit-by-id could be implemented later)
            startActivity(Intent(this, SortieEditActivity::class.java))
        }

        loadList()
    }

    private fun loadList() {
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) { db.sortieDao().getAll() }
            val items = list.map { s ->
                "${dateFmt.format(s.date)}  ${s.aircraftReg.ifEmpty { "N/A" }}  Off:${s.offBlock} On:${s.onBlock}"
            }
            lvSorties.adapter = ArrayAdapter(this@SortieListActivity, android.R.layout.simple_list_item_1, items)
        }
    }

    private fun toast(msg: String) = android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_LONG).show()
}
