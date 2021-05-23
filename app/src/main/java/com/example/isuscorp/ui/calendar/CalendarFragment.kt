package com.example.isuscorp.ui.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.example.isuscorp.IsusCorpApp
import com.example.isuscorp.R
import com.example.isuscorp.databinding.CalendarFragmentBinding
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import java.util.*

class CalendarFragment : Fragment() {

    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModel.CalendarViewModelFactory((activity?.application as IsusCorpApp).repository)
    }

    lateinit var binding: CalendarFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.calendar_fragment, container, false)

        addEvent()

        binding.compactcalendarView.setListener(object : CompactCalendarViewListener {

            val myItems: MutableList<String> = mutableListOf()

            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = binding.compactcalendarView.getEvents(dateClicked)
                if (events.isNotEmpty()) {
                    MaterialDialog(binding.root.context).show {
                        title(R.string.dialog_event)
                        cornerRadius(16f)
                        events.map {
                            myItems.add(it.data.toString())
                        }
                        listItems(items = myItems)

                        positiveButton {
                            myItems.clear()
                            this.dismiss()
                        }
                    }
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {

            }
        })

        binding.syncCalendar.setOnClickListener {
            syncCalendar()
        }

        return binding.root
    }

    fun syncCalendar() {
        calendarViewModel.allWords.observe(viewLifecycleOwner, { list ->
            list.map {
                val intent = Intent(Intent.ACTION_EDIT)
                intent.type = "vnd.android.cursor.item/event"
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, it.dateticket)
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                intent.putExtra(CalendarContract.Events.TITLE, "Client Name: ${it.nameclient}")
                startActivity(intent)
            }
        })
    }

    private fun addEvent() {
        calendarViewModel.allWords.observe(viewLifecycleOwner, { list ->
            if (list.isNullOrEmpty()) {
                binding.noEventsLayout.visibility = View.VISIBLE
                binding.compactcalendarView.visibility = View.GONE
            } else {
                binding.noEventsLayout.visibility = View.GONE
                binding.compactcalendarView.visibility = View.VISIBLE
                list.map {
                    binding.compactcalendarView.addEvent(
                        Event(
                            Color.BLUE,
                            it.dateticket!!.time,
                            "Client Name: ${it.nameclient}"
                        )
                    )
                }
            }

        })
    }

}