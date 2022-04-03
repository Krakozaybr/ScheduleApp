package com.krak.schedule_app.note;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.krak.schedule_app.R;
import com.krak.schedule_app.app.App;
import com.krak.schedule_app.app.database.NoteDao;
import com.krak.schedule_app.databinding.NoteActivityBinding;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.entities.Note;

public class NoteActivity extends AppCompatActivity {

    private NoteActivityBinding binding;
    public static final String PURPOSE = "PURPOSE";
    public static final String EDIT_NOTE = "EDIT_NOTE";
    public static final String NOTE_TO_EDIT = "NOTE_TO_EDIT";
    public static final String NEW_NOTE = "NEW_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NoteActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addContent();
        addEventListeners();
    }

    private void addEventListeners() {
        binding.cancelNoteBtn.setOnClickListener(view -> {
            startActivity(new Intent(NoteActivity.this, MainActivity.class));
        });
        binding.saveNoteBtn.setOnClickListener(view -> {
            String purpose = getIntent().getStringExtra(PURPOSE);
            if (purpose.equals(EDIT_NOTE)){
                save(Note.parse(getIntent().getStringExtra(NOTE_TO_EDIT)));
            } else {
                save(null);
            }
            startActivity(new Intent(NoteActivity.this, MainActivity.class));
        });
    }

    private void addContent(){
        String purpose = getIntent().getStringExtra(PURPOSE);
        switch (purpose){
            case EDIT_NOTE:
                Note note = Note.parse(getIntent().getStringExtra(NOTE_TO_EDIT));
                binding.titleNoteInput.setText(note.getTitle());
                binding.textNoteInput.setText(note.getText());
                binding.noteTitle.setImageResource(R.drawable.edit_note_title);
                break;
            case NEW_NOTE:
                binding.noteTitle.setImageResource(R.drawable.new_note_title);
                break;
        }

    }

    private void save(Note deleted){
        Note note = new Note(
                binding.titleNoteInput.getText().toString(),
                binding.textNoteInput.getText().toString()
        );
        new Thread(() -> {
            App app = (App) getApplication();
            NoteDao dao = app.getDatabase().notesDao();
            if (deleted != null){
                dao.delete(deleted);
            }
            dao.insert(note);
        }).start();
    }
}