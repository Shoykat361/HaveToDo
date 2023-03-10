package com.example.havetodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.havetodo.Dailogue.DatePickerDialogFragment
import com.example.havetodo.Dailogue.TimePickerDialogFragment
import com.example.havetodo.PreferenceData.LoginPreference
import com.example.havetodo.Utlis.getFormattedDateTime
import com.example.havetodo.Utlis.priority_high
import com.example.havetodo.Utlis.priority_normal
import com.example.havetodo.ViewModels.TodoViewModel
import com.example.havetodo.databinding.FragmentNewTodoBinding
import com.example.havetodo.entities.TodoModel
import com.example.havetodo.scheduletodo.WorkManagerUtils
import java.util.*

class NewTodoFragment : Fragment() {
    private lateinit var binding: FragmentNewTodoBinding
    private val todoViewModel: TodoViewModel by viewModels()
    private lateinit var preference: LoginPreference
    var priority = priority_normal
    var dateInMillis = System.currentTimeMillis()
    var timeInMillis = System.currentTimeMillis()
    private var selectedDate = ""
    private var selectedTime = ""
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0
    private var userId = 0L
    private var id: Long? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDays()
        preference = LoginPreference(requireContext())
        preference.userIdFlow.asLiveData()
            .observe(viewLifecycleOwner) {
                userId = it
            }
        binding = FragmentNewTodoBinding.inflate(inflater, container, false)

        id = arguments?.getLong("id")

        if (id != null) {
            binding.saveBtn.setText("update")
            todoViewModel.getTODoById(id!!).observe(viewLifecycleOwner) {

                binding.todoNameInputET.setText(it.name)
                if (it.priority == "Low") {
                    binding.priorityRG.check(R.id.lowRB)
                } else if (it.priority == "Normal") {
                    binding.priorityRG.check(R.id.normalRB)
                } else if (it.priority == "High") {
                    binding.priorityRG.check(R.id.highRB)
                }

                binding.dateTV.text = getFormattedDateTime(it.date, "dd/MM/yyyy")

                binding.timeTV.text = getFormattedDateTime(it.time, "hh:mm a")

            }
        }

        binding.priorityRG.setOnCheckedChangeListener { group, checkedId ->
            val rb: RadioButton = group.findViewById(checkedId)
            priority = rb.text.toString()
        }
        binding.dateBtn.setOnClickListener {
            DatePickerDialogFragment{
                binding.dateTV.text=it
            }.show(childFragmentManager, "Dateicker")

        }
        binding.timeBtn.setOnClickListener {
            TimePickerDialogFragment {
                binding.timeTV.text = it
            }.show(childFragmentManager, "Timeicker")
        }
        todoViewModel.idLD.observe(viewLifecycleOwner) { newID ->
            if (priority == priority_high) {
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day, hour, minute)
                val delay = calendar.timeInMillis - System.currentTimeMillis()
                WorkManagerUtils().schedule(
                    context = requireContext(),
                    name = binding.todoNameInputET.text.toString(),
                    todoID = newID,
                    delay = delay
                )
            }
        }
        binding.saveBtn.setOnClickListener {

            val name = binding.todoNameInputET.text.toString()
            val todo = TodoModel(
                name = name,
                userId = userId,
                priority = priority,
                date = dateInMillis,
                time = timeInMillis,
                day = day,
                month = month,
                year = year,
                hour = hour,
                minute = minute
            )


            if (id != null) {
                todo.todoId = id!!
                todoViewModel.updateTodo(todo)
            } else {
                todoViewModel.insertTodo(todo)
            }

            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun initDays() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
    }
}