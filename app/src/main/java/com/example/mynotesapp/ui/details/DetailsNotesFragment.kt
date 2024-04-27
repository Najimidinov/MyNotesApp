package com.example.mynotesapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.R
import com.example.mynotesapp.databinding.FragmentDetailsNoteBinding
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.ui.NotesViewModel

class DetailsNotesFragment : Fragment() {

    private val binding: FragmentDetailsNoteBinding by lazy {
        FragmentDetailsNoteBinding.inflate(layoutInflater)
    }

    private val viewModel: NotesViewModel by lazy {
        NotesViewModel(requireContext())
    }

    private val oldNote: Note by lazy {
        DetailsNotesFragmentArgs.fromBundle(requireArguments()).note
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
        initView()
    }

    private fun initView() {
        binding.apply {
            etTitleNote.setText(oldNote.title)
            etTypeSomethingNote.setText(oldNote.description)
        }
    }

    private fun clickListeners() {
        binding.apply {

            cvArrowLeft.setOnClickListener {
                findNavController().popBackStack()
            }


            cvConservation.setOnClickListener {
                val title = etTitleNote.text.toString()
                val description = etTypeSomethingNote.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {

                    val newNote = Note(
                        id = oldNote.id,
                        title = title,
                        description = description
                    )

                    viewModel.updateNote(oldNote = oldNote, newNote = newNote)
                    findNavController().popBackStack()

                } else toast(getString(R.string.txt_empty_add_note))
            }

        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}