package com.krak.schedule_app.ui.notes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.krak.schedule_app.R;
import com.krak.schedule_app.app.App;
import com.krak.schedule_app.app.database.NoteDao;
import com.krak.schedule_app.entities.Note;
import com.krak.schedule_app.livedata.NotesHolder;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.note.NoteActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private List<Note> notes;
    private LayoutInflater inflater;
    private MainActivity context;

    public NotesAdapter(MainActivity context, List<Note> notes) {
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notes_note, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Note note = notes.get(position);
        holder.text.setText(note.getText());
        holder.title.setText(note.getTitle());
        holder.note = note;
        holder.addListeners();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        private ImageButton edit, delete;
        private TextView title, text;
        private Note note;
        private LinearLayoutCompat notesNoteTitleContainer, notesNoteContainer;
        private LayoutTransition layoutTransition;
        private boolean isTextShowed;

        public NoteHolder(View itemView) {
            super(itemView);
            this.edit = itemView.findViewById(R.id.editNoteBtn);
            this.delete = itemView.findViewById(R.id.deleteNoteBtn);
            this.title = itemView.findViewById(R.id.notesNoteTitle);
            this.text = itemView.findViewById(R.id.notesNoteText);
            this.notesNoteTitleContainer = itemView.findViewById(R.id.notesNoteTitleContainer);
            this.notesNoteContainer = itemView.findViewById(R.id.notesNoteContainer);
            this.layoutTransition = new LayoutTransition();
            layoutTransition.setDuration(300);
            notesNoteContainer.setLayoutTransition(layoutTransition);
            text.setVisibility(View.GONE);
            this.isTextShowed = false;
        }

        private void addListeners(){
            this.edit.setOnClickListener(view -> {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra(NoteActivity.PURPOSE, NoteActivity.EDIT_NOTE);
                intent.putExtra(NoteActivity.NOTE_TO_EDIT, note.toJSON());
                context.startActivity(intent);
            });
            this.delete.setOnClickListener(view -> {
                new ViewModelProvider(context).get(NotesHolder.class).delete(note);
            });
            this.notesNoteTitleContainer.setOnClickListener(view -> {
                if (isTextShowed){
                    text.animate()
                        .translationY(text.getHeight())
                        .alpha(0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                text.setVisibility(View.GONE);
                            }
                        });
                } else{
                    text.animate()
                        .translationYBy(-text.getHeight()).translationY(0).setDuration(100)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                text.setVisibility(View.VISIBLE);
                            }
                        });
                }
                isTextShowed = !isTextShowed;
            });
        }
    }
}
