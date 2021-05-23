package com.example.isuscorp

import android.app.Application
import com.example.isuscorp.data.database.IsusCorpDB
import com.example.isuscorp.data.database.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class IsusCorpApp : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    /*
        * initializing the application database and repository
        * */
    val database by lazy { IsusCorpDB.getDatabase(this, applicationScope) }
    val repository by lazy { Repository(database.userDao(), database.ticketDao()) }
}