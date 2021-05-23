package com.example.isuscorp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ticket")
class Ticket(
    @PrimaryKey(autoGenerate = true)
    val tid: Int,
    @ColumnInfo(name = "nameclient") val nameclient: String?,
    @ColumnInfo(name = "phoneclient") val phoneclient: String?,
    @ColumnInfo(name = "dateticket") val dateticket: Date?,
    @ColumnInfo(name = "clientaddress") val clientaddress: String?,
) {
}