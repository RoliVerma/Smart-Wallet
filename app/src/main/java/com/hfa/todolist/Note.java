package com.hfa.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String type;
    private int priority;
    private String date;

    public Note(String title, String description,String type, int priority,String date) {
        this.title = title;
        this.description = description;
        this.type=type;
        this.priority = priority;
        this.date=date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getType() {
        return type;
    }
    public String getDate() {
        return date;
    }


    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }


}
