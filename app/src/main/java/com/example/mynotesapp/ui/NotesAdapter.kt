package com.example.mynotesapp.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.R
import com.example.mynotesapp.databinding.ItemListsBinding
import com.example.mynotesapp.model.Note

class NotesAdapter(
    val navigateToNoteDetails: (note: Note) -> Unit, val deleteNote: (note: Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notes: MutableList<Note> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(private val binding: ItemListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, position: Int) {
            binding.apply {
                tvAddNote.text = note.title

                root.setCardBackgroundColor(
                    when (position % 5) {
                        0 -> Color.LTGRAY
                        1 -> Color.GREEN
                        2 -> Color.MAGENTA
                        3 -> Color.YELLOW
                        else -> Color.BLUE
                    }
                )

                root.setOnClickListener {
                    navigateToNoteDetails(note)
                }

                root.setOnLongClickListener  {
                    deleteNote(note)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val resource = R.layout.item_lists
        val view = inflater.inflate(resource, parent, false)
        val binding = ItemListsBinding.bind(view)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position], position)
    }
}

