package com.example.isuscorp.ui.dashboard.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.example.isuscorp.IsusCorpApp
import com.example.isuscorp.R
import com.example.isuscorp.data.model.Ticket
import com.example.isuscorp.databinding.FragmentAddTicketBinding
import com.example.isuscorp.ui.dashboard.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

class EditTicketFragment: Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels {
        DashboardViewModel.DashBoardViewModelFactory((activity?.application as IsusCorpApp).repository)
    }

    var date = Date()

    lateinit var binding: FragmentAddTicketBinding

    lateinit var ticket: Ticket
    private var ticketId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_ticket, container, false)

        ticketId = arguments?.getInt("ticketId")!!

        ticket = dashboardViewModel.getTicketById(ticketId)

        binding.clientnameEdit.setText(ticket.nameclient)
        binding.dateText.text = toSimpleString(ticket.dateticket)
        binding.clientphoneEdit.setText(ticket.phoneclient)
        binding.clientdirEdit.setText(ticket.clientaddress)

        binding.setDate.setOnClickListener {
            MaterialDialog(binding.root.context).show {
                title(R.string.set_date)
                this.cornerRadius(16f)
                datePicker { dialog, datetime ->
                    date = datetime.time
                    binding.dateText.text = toSimpleString(date)
                }
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ticket_update -> {
                MaterialDialog(binding.root.context).show {
                    this.title(R.string.information)
                    this.message(R.string.message_update)

                    positiveButton {
                        updateTicket()
                        val bundle = bundleOf("ticket" to ticketId)
                        binding.root.findNavController().navigate(R.id.action_editTicketFragment_to_ticketDetailsFragment,bundle)
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateTicket(){
        val nametoUpdate = binding.clientnameEdit.text.toString()
        val phonetoUpdate = binding.clientphoneEdit.text.toString()
        val datetoUpdate = date
        val addresstoUpdate = binding.clientdirEdit.text.toString()

        val ticketToUpdate = Ticket(ticketId,nametoUpdate,phonetoUpdate,datetoUpdate,addresstoUpdate)

        dashboardViewModel.updateTicket(ticketToUpdate)
    }

    @SuppressLint("SimpleDateFormat")
    private fun toSimpleString(date: Date?): String = with(date ?: Date()) {
        SimpleDateFormat("dd/MM/yyyy").format(this)
    }
}