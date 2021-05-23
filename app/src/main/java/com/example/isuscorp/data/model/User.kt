package com.example.isuscorp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//the following class defines the user entity that would be our model in the database room
@Entity(tableName = "user")
class User(

    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "password") val password: String?,

    )