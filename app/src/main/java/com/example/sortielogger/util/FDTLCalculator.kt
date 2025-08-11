package com.example.sortielogger.util

import com.example.sortielogger.model.Sortie
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.LinkedHashMap

object FDTLCalculator {
    private val maxFlightHours = linkedMapOf(
        7 to 35.0,
        14 to 65.0,
        28 to 100.0,
        90 to 300.0,
        365 to 1000.0
    )

    private val fmt = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }

    // block time (minutes) = onBlock - offBlock (handles cross-midnight)
    private fun blockMinutes(s: Sortie): Long {
        return try {
            val off = fmt.parse(s.offBlock)
            val on = fmt.parse(s.onBlock)
            if (off != null && on != null) {
                var diff = on.time - off.time
                if (diff < 0) diff += TimeUnit.DAYS.toMillis(1) // crossed midnight
                if (diff > 0) TimeUnit.MILLISECONDS.toMinutes(diff) else 0L
            } else 0L
        } catch (e: Exception) { 0L }
    }

    private fun totalHoursLastDays(sorties: List<Sortie>, days: Int): Double {
        val cutoff = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(days.toLong()))
        var totalMin = 0L
        sorties.forEach { s ->
            if (s.date.after(cutoff)) totalMin += blockMinutes(s)
        }
        return totalMin.toDouble() / 60.0
    }

    fun checkLimitsUsingBlockTime(sorties: List<Sortie>): Map<Int, Pair<Double, Double>> {
        val out = LinkedHashMap<Int, Pair<Double, Double>>()
        maxFlightHours.forEach { (days, max) ->
            out[days] = Pair(totalHoursLastDays(sorties, days), max)
        }
        return out
    }
}
