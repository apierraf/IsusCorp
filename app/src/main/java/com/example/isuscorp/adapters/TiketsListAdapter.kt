package com.example.isuscorp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.isuscorp.data.model.Ticket
import com.example.isuscorp.databinding.ItemTicketListBinding
import com.example.isuscorp.misc.ItemClick
import java.text.SimpleDateFormat
import java.util.*

class TiketsListAdapter(val listTicket: List<Ticket>, val itemClick: ItemClick): RecyclerView.Adapter<TiketsListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTicketListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listTicket[position]){
                binding.clientName.text = this.nameclient
                binding.dateTicket.text = toSimpleString(this.dateticket)
                binding.viewTicket.setOnClickListener {
                    itemClick.itemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listTicket.size
    }

    //format string date
    @SuppressLint("SimpleDateFormat")
    private fun toSimpleString(date: Date?): String = with(date ?: Date()) {
        SimpleDateFormat("dd/MM/yyyy").format(this)
    }
}