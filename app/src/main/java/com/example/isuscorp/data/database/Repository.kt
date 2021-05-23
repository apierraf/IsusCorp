package com.example.isuscorp.data.database

import androidx.lifecycle.LiveData
import com.example.isuscorp.data.dao.TicketDao
import com.example.isuscorp.data.dao.UserDao
import com.example.isuscorp.data.model.Ticket
import com.example.isuscorp.data.model.User
import kotlinx.coroutines.flow.Flow

class Repository(private val userDao: UserDao, private val ticketDao: TicketDao) {

    fun getUser(userName: String, password: String): User? {
        return userDao.findUser(userName, password)
    }

    fun insertUser(user: User){
        userDao.insertAll(user)
    }

    fun getAllTicket(): Flow<List<Ticket>> {
        return ticketDao.getAllTickets()
    }

    fun getTicketById(tid: Int): Ticket {
        return ticketDao.getTicketsByID(tid)
    }

    suspend fun insetTicket(ticket: Ticket){
        ticketDao.insertTicket(ticket)
    }

    suspend fun updateTicket(ticket: Ticket){
        ticketDao.updateTicket(ticket)
    }

    suspend fun deleTicket(tid: Int){
        ticketDao.deleteTicketFromID(tid)
    }
}