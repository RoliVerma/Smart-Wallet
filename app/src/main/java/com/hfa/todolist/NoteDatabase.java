package com.hfa.todolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {Note.class}, version =1, exportSchema = false)
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

    public static class PopulateInit extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","This is first",1));
            noteDao.insert(new Note("Title2","This is second",2));
            noteDao.insert(new Note("Title3","This is third",3));
            return null;
        }
    }
}
