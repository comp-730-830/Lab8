package com.example.notepad.features.list.view;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.R;
import com.example.notepad.data.Note;
import com.example.notepad.features.list.viewmodel.NotesViewModel;
import com.example.notepad.features.list.viewmodel.state.DeleteConfirmationState;
import com.example.notepad.features.list.viewmodel.state.ErrorState;
import com.example.notepad.features.list.viewmodel.state.NotesListState;
import com.example.notepad.navigation.NavigationActivity;
import com.example.notepad.observable.Observer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesListActivity extends NavigationActivity {

    private RecyclerView listView;
    private FloatingActionButton createButton;
    private NotesAdapter adapter;

    private NotesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        viewModel = new NotesViewModel();

        initListView();
        initCreateButton();
        observeData();
    }

    private void initListView() {
        listView = findViewById(R.id.listView);
        adapter = new NotesAdapter();
        adapter.setDeleteListener(viewModel);
        adapter.setEditListener(viewModel);
        listView.setAdapter(adapter);
    }

    private void initCreateButton() {
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> viewModel.onCreateClick());
    }

    private void observeData() {
        viewModel.notes.registerObserver(new Observer<List<Note>>() {
            @Override
            public void notify(List<Note> data) {
                {
                    adapter.submitList(data);
                }
            }
        });
        viewModel.state.registerObserver(new Observer<NotesListState>() {
            @Override
            public void notify(NotesListState data) {
                if (data instanceof DeleteConfirmationState) {
                    showDeleteConfirmation(((DeleteConfirmationState) data).getNote());
                } else if (data instanceof ErrorState) {
                    ((ErrorState) data).getError().printStackTrace();
                }
            }
        });
    }

    public void showDeleteConfirmation(Note note) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete the note? This can't be undone.")
            .setPositiveButton("Delete", (dialog, which) -> viewModel.onConfirmDeleteClick(note))
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    protected void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }
}