package com.example.notes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale


class NotesAdapter(private  var notes: List<Note>, private val context: MainActivity): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)
    class NoteViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contextTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val priorityTextView: TextView = itemView.findViewById(R.id.priorityTextView)

        //update
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        //delete
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contextTextView.text = note.content
        holder.priorityTextView.text = context.resources.getStringArray(R.array.priority_levels)[note.priority]

        val backgroundColor = when (note.priority) {
            0 -> ContextCompat.getColor(context, R.color.white) // Low priority
            1 -> ContextCompat.getColor(context, R.color.green) // Medium priority
            2 -> ContextCompat.getColor(context, R.color.yellow) // High priority
            else -> ContextCompat.getColor(context, R.color.red) // Default color
        }

        holder.cardView.setCardBackgroundColor(backgroundColor)

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes : List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}