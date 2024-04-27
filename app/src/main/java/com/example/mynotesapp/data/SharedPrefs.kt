package com.example.mynotesapp.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.mynotesapp.utils.EMPTY_STRING
import com.example.mynotesapp.model.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefs(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(KEY_SHARED_PREFS, 0)
    }

    fun getAllNotes(): List<Note> {
        val gson = Gson()
        val json = sharedPreferences.getString(KEY_ALL_NOTES, null)
        val type = object : TypeToken<List<Note>>() {}.type
        val list = gson.fromJson<List<Note>>(json, type)
        return list ?: emptyList()
    }

    fun saveNote(): Note {
        val note = Note(title = EMPTY_STRING, description = EMPTY_STRING)
        val allNotes = getAllNotes().toMutableList()
        allNotes.add(0, note)
        sharedPreferences(allNotes)
        return note
    }

    fun updateNote(oldNote: Note, newNote: Note) {
        val allNotes = getAllNotes().toMutableList()
        var getIndex = -1
        allNotes.forEachIndexed { index, note -> if (note.id == oldNote.id) getIndex = index }
        allNotes[getIndex] = newNote
        sharedPreferences(allNotes)
    }

    fun deleteNote(note: Note) {
        val allNotes = getAllNotes().toMutableList()
        allNotes.removeIf { note.id == it.id }
        sharedPreferences(allNotes)
    }

    private fun sharedPreferences(allNotes: MutableList<Note>) {
        val json = Gson().toJson(allNotes)
        sharedPreferences.edit {
            putString(KEY_ALL_NOTES, json)
            apply()
        }
    }


    companion object {
        private const val KEY_SHARED_PREFS = "KEY_SHARED_PREFS"
        private const val KEY_ALL_NOTES = "KEY_ALL_NOTES"
    }
}