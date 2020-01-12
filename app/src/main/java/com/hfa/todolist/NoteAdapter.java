package com.hfa.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteHolder> {

    private OnItemClickListener listener;
    public AdapterView.OnItemSelectedListener spinner_listener;

    protected NoteAdapter() {
        super(DIFF_CALLBACK);

    }

    public  static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return ((oldItem.getDescription()== newItem.getDescription())&&(oldItem.getDate()==newItem.getDate())
                    &&(oldItem.getPriority()== newItem.getPriority()) &&(
                       oldItem.getType()== newItem.getType())&&
                    ( oldItem.getTitle()== newItem.getTitle()));
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteHolder(viewHolder);
    }

    public Note getNoteAt(int position){
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
    Note currentNote = getItem(position);
    holder.Titletv.setText(currentNote.getTitle());
    holder.dateTv.setText(currentNote.getDate());
    holder.Desctv.setText(currentNote.getDescription());
    //holder.spinner.setSelection(0);
    //String compare=currentNote.getType();
    }


    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView Titletv,Desctv, dateTv;
        private Spinner spinner;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);//here the item view is the card itself
            Titletv=itemView.findViewById(R.id.title_tv);
            Desctv=itemView.findViewById(R.id.description_tv);
            dateTv =itemView.findViewById(R.id.priority_tv);
           // spinner=itemView.findViewById(R.id.spinner);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));//calling one declared by us
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
         void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
