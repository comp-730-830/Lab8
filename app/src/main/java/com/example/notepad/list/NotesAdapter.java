package com.example.notepad.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.data.Note;
import com.example.notepad.R;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.ViewHolder> {

    private OnEditListener editListener = null;
    private OnDeleteListener deleteListener = null;

    public NotesAdapter() {
        super(new DiffUtil.ItemCallback<Note>() {
            @Override
            public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                return oldItem.getContent().equals(newItem.getContent());
            }
        });
    }

    protected NotesAdapter(@NonNull DiffUtil.ItemCallback<Note> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void setEditListener(OnEditListener editListener) {
        this.editListener = editListener;
    }

    public void setDeleteListener(OnDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View editButton;
        private View deleteButton;
        private TextView contentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentView = itemView.findViewById(R.id.content);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(Note note) {
            contentView.setText(note.getContent());
            View.OnClickListener onEditClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editListener != null) {
                        editListener.onEditClick(note);
                    }
                }
            };
            View.OnClickListener onDeleteClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null) {
                        deleteListener.onDeleteClick(note);
                    }
                }
            };
            editButton.setOnClickListener(onEditClick);
            deleteButton.setOnClickListener(onDeleteClick);
            itemView.setOnClickListener(onEditClick);
        }
    }

    interface OnEditListener {
        void onEditClick(Note note);
    }

    interface OnDeleteListener {
        void onDeleteClick(Note note);
    }
}
