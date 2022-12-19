package com.example.havetodo.Dailogue

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.havetodo.Utlis.getFormattedDateTime
import java.util.*

class TimePickerDialogFragment(val callback:(String)->Unit): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of DatePickerDialog and return it
        return TimePickerDialog(requireActivity(),this,hour,minute, true)

    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val c = Calendar.getInstance()
        c.set(0,0,0, p1,p2)
        val selecttime= getFormattedDateTime(c.timeInMillis,"HH:mm" )
        callback(selecttime)
    }
}