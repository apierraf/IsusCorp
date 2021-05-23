package com.example.isuscorp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.isuscorp.data.model.Ticket
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {

    @Query("SELECT * FROM ticket")
    fun getAllTickets(): Flow<List<Ticket>>

    @Query("SELECT * FROM ticket WHERE tid = (:tid)")
    fun getTicketsByID(tid: Int): Ticket

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTicket(ticket: Ticket)

    @Query("DELETE FROM ticket WHERE tid = (:tid)")
    suspend fun deleteTicketFromID(tid: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTicket(ticket: Ticket)
}