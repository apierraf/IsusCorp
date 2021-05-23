package com.example.isuscorp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.isuscorp.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE username = (:userName) AND password = (:password)")
    fun findUser(userName: String, password: String ): User?

    @Insert
    fun insertAll(vararg users: User)

}