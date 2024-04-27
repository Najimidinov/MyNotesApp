package com.example.mynotesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.databinding.FragmentNotesBinding
import com.example.mynotesapp.model.Note

class NoteFragment : Fragment() {

    private val binding: FragmentNotesBinding by lazy {
        FragmentNotesBinding.inflate(layoutInflater)
    }

    private val viewModel: NotesViewModel by lazy {
        NotesViewModel(requireContext())
    }

    private val adapter: NotesAdapter by lazy {
        NotesAdapter(navigateToNoteDetails = { note ->
            findNavController().navigate(
                NoteFragmentDirections.actionDetailsNotesFragmentToDetailsNotesFragment(note)
            )
        }, deleteNote = { note ->
            list = viewModel.deleteNote(note)
            setViewVisibility()
        })
    }

    private var list: List<Note> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = viewModel.getAllNotes()
        clickListener()

        setViewVisibility()

        binding.rvNotes.adapter = adapter

    }

    private fun setViewVisibility() {
        if (list.isEmpty()) {
            binding.rvNotes.visibility = View.GONE
            binding.llEmptyNotes.visibility = View.VISIBLE
        } else {
            binding.rvNotes.visibility = View.VISIBLE
            binding.llEmptyNotes.visibility = View.GONE
        }
        adapter.updateNotes(newNotes = list)
    }

    private fun clickListener() = binding.apply {
        cvPlus.setOnClickListener {
            val note = viewModel.saveNote()
            findNavController().navigate(
                NoteFragmentDirections.actionDetailsNotesFragmentToDetailsNotesFragment(note)
            )
        }

        cvSearch.setOnClickListener {
            findNavController().navigate(NoteFragmentDirections.actionNotesFragmentToEmptySearchFragment())
        }
    }

}