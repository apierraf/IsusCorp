package com.example.isuscorp.ui.dashboard.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
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

class AddTicketFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels {
        DashboardViewModel.DashBoardViewModelFactory((activity?.application as IsusCorpApp).repository)
    }

    var date = Date()

    lateinit var binding: FragmentAddTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_ticket, container, false)


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
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_ticket -> addTicket()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addTicket() {
        val nameClient = binding.clientnameEdit.text.toString()
        val phoneClient = binding.clientphoneEdit.text.toString()
        val dateTiket = date
        val addressTicket = binding.clientdirEdit.text.toString()

        val ticket = Ticket(0, nameClient, phoneClient, dateTiket, addressTicket)
        dashboardViewModel.insertTicket(ticket)

        binding.root.findNavController().navigate(R.id.action_addTicketFragment_to_dashboardFragment)
    }

    @SuppressLint("SimpleDateFormat")
    private fun toSimpleString(date: Date?): String = with(date ?: Date()) {
        SimpleDateFormat("dd/MM/yyyy").format(this)
    }
}