package com.example.sortielogger.util

import android.content.Context
import com.example.sortielogger.model.Sortie
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object CsvExporter {
    private val dateFmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }

    fun exportSorties(context: Context, sorties: List<Sortie>): File {
        val file = File(context.getExternalFilesDir(null), "Sortie_Report_${System.currentTimeMillis()}.csv")
        FileWriter(file).use { fw ->
            fw.append("Date,Aircraft,OffBlock,Airborne,Touchdown,OnBlock,Remarks\n")
            sorties.forEach { s ->
                val line = listOf(
                    dateFmt.format(s.date),
                    escape(s.aircraftReg),
                    escape(s.offBlock),
                    escape(s.airborne),
                    escape(s.touchdown),
                    escape(s.onBlock),
                    escape(s.remarks)
                ).joinToString(",")
                fw.append(line).append("\n")
            }
        }
        return file
    }

    private fun escape(str: String) = "\"${str.replace("\"","\"\"")}\""
}
