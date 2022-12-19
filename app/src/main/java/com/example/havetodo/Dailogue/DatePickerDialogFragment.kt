package com.example.havetodo.Dailogue

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.havetodo.Utlis.getFormattedDateTime
import java.util.*


class DatePickerDialogFragment(val callback:(String)->Unit):
    DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireActivity(), this, year, month, day)

    }
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val c = Calendar.getInstance()
        c.set(p1,p2,p3)
        val selectdate= getFormattedDateTime(c.timeInMillis,"dd/MM/yyyy" )
        //getformatedDatetime(c.timeInMillis,"dd/MM/yyyy" )
        callback(selectdate)
    }
}