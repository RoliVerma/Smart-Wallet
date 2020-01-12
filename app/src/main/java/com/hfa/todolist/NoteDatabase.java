package com.hfa.todolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version =2, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    //singleton
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();//room subclasses our abstract class due to line 21,22

    //synchronize so that only one thread can access the functn at a time,to avoid multiple instances
    public static synchronized NoteDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class
            ,"note_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

//for testing purpose only
    public static class PopulateInit extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","This is first","Received",2,"11/12/2020"));
            noteDao.insert(new Note("Title2","This is second","Spent",10,"11/10/2020"));
            noteDao.insert(new Note("Title3","This is third","Borrowed",3,"13/04/2019"));
            return null;
        }
    }
}
