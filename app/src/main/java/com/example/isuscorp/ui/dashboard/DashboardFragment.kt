package com.example.isuscorp.ui.dashboard

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isuscorp.IsusCorpApp
import com.example.isuscorp.R
import com.example.isuscorp.adapters.TiketsListAdapter
import com.example.isuscorp.data.model.Ticket
import com.example.isuscorp.databinding.DashboardFragmentBinding
import com.example.isuscorp.misc.ItemClick
import kotlinx.coroutines.launch
import java.util.*

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels {
        DashboardViewModel.DashBoardViewModelFactory((activity?.application as IsusCorpApp).repository)
    }

    lateinit var ticketAdapter: TiketsListAdapter
    lateinit var binding: DashboardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false)

        binding.addTickets.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboardFragment_to_addTicketFragment)
        }

        poblateRecyclerTickets()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.calendar_menu ->{
                binding.root.findNavController().navigate(R.id.action_dashboardFragment_to_calendarFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun poblateRecyclerTickets() {
        dashboardViewModel.allTikets.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.noticketLayout.visibility = View.VISIBLE
                binding.rvListTickets.visibility = View.GONE
            } else {
                binding.noticketLayout.visibility = View.GONE
                binding.rvListTickets.visibility = View.VISIBLE
                ticketAdapter = TiketsListAdapter(it,object : ItemClick{
                    override fun itemClick(pos: Int) {
                        val bundle = bundleOf("ticket" to it[pos].tid)
                        binding.root.findNavController().navigate(R.id.action_dashboardFragment_to_ticketDetailsFragment,bundle)
                    }
                })
                binding.rvListTickets.adapter = ticketAdapter
                binding.rvListTickets.layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            }
        })
    }
}