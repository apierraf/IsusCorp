package com.example.isuscorp.ui.dashboard

import androidx.lifecycle.*
import com.example.isuscorp.data.database.Repository
import com.example.isuscorp.data.model.Ticket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: Repository) : ViewModel() {

    val allTikets = repository.getAllTicket().asLiveData()

    fun getTicketById(tid: Int): Ticket {
        return repository.getTicketById(tid)
    }

    fun insertTicket(ticket: Ticket) {
        viewModelScope.launch {
            repository.insetTicket(ticket)
        }
    }

    fun deleteTicket(tid: Int) {
        viewModelScope.launch {
            repository.deleTicket(tid)
        }
    }

    fun updateTicket(ticket: Ticket) {
        viewModelScope.launch {
            repository.updateTicket(ticket)
        }
    }

    class DashBoardViewModelFactory(private val repository: Repository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DashboardViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}