package com.example.sortielogger.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sorties")
data class Sortie(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Date,
    val aircraftReg: String = "",
    val offBlock: String = "",    // HH:mm:ss
    val airborne: String = "",
    val touchdown: String = "",
    val onBlock: String = "",     // HH:mm:ss
    val remarks: String = ""
)
