package com.krak.schedule_app.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.krak.schedule_app.R;
import com.krak.schedule_app.databinding.NotesFragmentBinding;
import com.krak.schedule_app.entities.Note;
import com.krak.schedule_app.livedata.NotesHolder;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.note.NoteActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment{

    private NotesFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = NotesFragmentBinding.inflate(inflater);
        addListeners();
        initNotes();
        MainActivity.saveable = null;
        return binding.getRoot();
    }

    public void initNotes(){
        NotesAdapter adapter = new NotesAdapter((MainActivity) getActivity(), getInitialData());
        binding.notes.setAdapter(adapter);
    }

    public List<Note> getInitialData(){
        return new ViewModelProvider(getActivity()).get(NotesHolder.class).getData().getValue();
    }

    public void addListeners(){
        binding.addNoteNotes.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), NoteActivity.class);
            intent.putExtra(NoteActivity.PURPOSE, NoteActivity.NEW_NOTE);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}