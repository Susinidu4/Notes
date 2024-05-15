package com.example.notes

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityAddNoteBinding
import java.util.Calendar

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db:NotesDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        val priorityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.priority_levels,
            android.R.layout.simple_spinner_item
        )
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = priorityAdapter

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val priority = binding.prioritySpinner.selectedItemPosition

//            val day = binding.dateEditPicker.dayOfMonth
//            val month = binding.dateEditPicker.month
//            val year = binding.dateEditPicker.year
//            val calendar = Calendar.getInstance()
//            calendar.set(year, month, day)
//            val date = calendar.time

                val note = Note(0, title, content, priority)//, date)
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()

        }
        
        
    }
}