package com.hfa.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID="com.hfa.todolist.EXTRA_ID";
    public static final String EXTRA_TITLE="com.hfa.todolist.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="com.hfa.todolist.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY="com.hfa.todolist.EXTRA_PRIORITY";

    private EditText editTextTitle, editTextDescription;
    private TextView textViewDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle=findViewById(R.id.edit_text_title);
        editTextDescription=findViewById(R.id.edit_text_description);
        textViewDescription=findViewById(R.id.description_tv);
        numberPickerPriority=findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);//to get close icon on topleft on action bar
        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }
        else
        setTitle("Add Note");
    }

    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority=numberPickerPriority.getValue();

        if(title.trim().isEmpty()|| description.trim().isEmpty()){
            Toast.makeText(this,"Please enter title and description",Toast.LENGTH_LONG).show();
            return;
        }
        Intent dataIntent= new Intent();
        dataIntent.putExtra(EXTRA_TITLE,title);
        dataIntent.putExtra(EXTRA_DESCRIPTION,description);
        dataIntent.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            dataIntent.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,dataIntent); //to track if task of saving note is done we use  RESULT_OK which is just an int constant
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);//1st argument is menu.xml, 2nd is the parameter
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
