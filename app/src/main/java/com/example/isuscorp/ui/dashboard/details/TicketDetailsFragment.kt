package com.example.isuscorp.ui.dashboard.details

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.example.isuscorp.IsusCorpApp
import com.example.isuscorp.R
import com.example.isuscorp.data.model.Ticket
import com.example.isuscorp.databinding.FragmentTicketDetailsBinding
import com.example.isuscorp.ui.dashboard.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

class TicketDetailsFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels {
        DashboardViewModel.DashBoardViewModelFactory((activity?.application as IsusCorpApp).repository)
    }
    lateinit var binding: FragmentTicketDetailsBinding
    lateinit var ticket: Ticket
    private var ticketId: Int = 0
    private val requestCall = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ticket_details, container, false)

        ticketId = arguments?.getInt("ticket")!!

        ticket = dashboardViewModel.getTicketById(ticketId)

        poblateDetailsTickets()

        binding.actionCall.setOnClickListener {
            makePhoneCall()
        }

        binding.mapsDetail.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=${ticket.clientaddress}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_ticket -> {
            //Confirm delete ticket dialog
                MaterialDialog(binding.root.context).show {
                    this.title(R.string.delete_ticket)
                    this.cornerRadius(16f)
                    this.message(R.string.message_delete_ticket)
                    positiveButton {
                        dashboardViewModel.deleteTicket(ticketId)
                        binding.root.findNavController()
                            .navigate(R.id.action_ticketDetailsFragment_to_dashboardFragment)
                    }
                    negativeButton {
                        this.dismiss()
                    }
                }
            }
            R.id.update_ticket -> {
                val bundle = bundleOf("ticketId" to ticketId)
                binding.root.findNavController().navigate(R.id.action_ticketDetailsFragment_to_editTicketFragment, bundle)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun poblateDetailsTickets() {
        binding.nameclientDetails.text = ticket.nameclient
        binding.dateclientDetails.text = toSimpleString(ticket.dateticket)
        binding.phoneclientDetails.text = ticket.phoneclient
    }

    @SuppressLint("SimpleDateFormat")
    private fun toSimpleString(date: Date?): String = with(date ?: Date()) {
        SimpleDateFormat("dd/MM/yyyy").format(this)
    }
    private fun makePhoneCall() {
        val number: String = binding.phoneclientDetails.text.toString()
        if (number.trim { it <= ' ' }.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(
                    binding.root.context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    requestCall
                )
            } else {
                val dial = "tel:$number"
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        } else {
           // Toast.makeText(binding.root.context, "Enter Phone Number", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == requestCall) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(binding.root.context, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}