package com.example.mynotesapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mynotesapp.data.SharedPrefs
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.utils.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow

class NotesViewModel(context: Context) : ViewModel() {

    private val sharedPrefs: SharedPrefs by lazy { SharedPrefs(context) }

    fun saveNote(): Note {
        return sharedPrefs.saveNote()
    }

    fun updateNote(oldNote: Note, newNote: Note) {
        sharedPrefs.updateNote(oldNote = oldNote, newNote = newNote)
    }

    fun getAllNotes(): List<Note> {
        return filterNotes(sharedPrefs.getAllNotes())
    }

    fun updateSearchText(newText: String):List<Note> {
        return getAllNotes().toMutableList().filter { it.title.contains(newText) }
    }

    fun deleteNote(note: Note): List<Note> {
        sharedPrefs.deleteNote(note = note)
        return filterNotes(sharedPrefs.getAllNotes())
    }

    private fun filterNotes(notes: List<Note>): List<Note> {
        val filterTitle = notes.filter { it.title.isNotEmpty() }.toMutableList()
        return filterTitle.filter { it.description.isNotEmpty() }
    }
}