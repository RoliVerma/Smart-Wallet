package com.hfa.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete (Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY date ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT COUNT(*) FROM note_table where type='Spent' ")
    int countSpent();

    @Query("SELECT COUNT(*) FROM note_table where type='Lent' ")
    int countLent();

    @Query("SELECT COUNT(*) FROM note_table where type='Borrowed' ")
    int countBorrowed();

    @Query("SELECT COUNT(*) FROM note_table where type='Received' ")
    int countReceived();
}

