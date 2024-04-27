package com.example.mynotesapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.databinding.FragmentSearchBinding
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.ui.NoteFragmentDirections
import com.example.mynotesapp.ui.NotesAdapter
import com.example.mynotesapp.ui.NotesViewModel
import com.example.mynotesapp.utils.EMPTY_STRING

class SearchFragment : Fragment() {

    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = viewModel.getAllNotes()
        observeSearchText()
        setViewVisibility()
    }

    private fun observeSearchText() {
        binding.svNotesSearch.setOnQueryTextListener(
            object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    list = viewModel.updateSearchText(query ?: EMPTY_STRING)
                    setViewVisibility()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    list = viewModel.updateSearchText(newText ?: EMPTY_STRING)
                    setViewVisibility()
                    return false
                }
            }
        )

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
}