package com.hfa.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ID="com.hfa.todolist.EXTRA_ID";
    public static final String EXTRA_TITLE="com.hfa.todolist.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="com.hfa.todolist.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE="com.hfa.todolist.EXTRA_DATE";
    public static final String EXTRA_PRIORITY="com.hfa.todolist.EXTRA_PRIORITY";
    public static final String EXTRA_TYPE="com.hfa.todolist.EXTRA_TYPE";

    private EditText editTextAmount, editTextDescription;
    private TextView textViewDescription,dateTextView;
    private NumberPicker numberPickerPriority;
    private Spinner spinner;
    private Button changeDate;
    public  ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        spinner=findViewById(R.id.spinner);
        editTextAmount =findViewById(R.id.edit_text_amount);
        editTextDescription=findViewById(R.id.edit_text_description);
        textViewDescription=findViewById(R.id.description_tv);
        numberPickerPriority=findViewById(R.id.number_picker_priority);
        dateTextView=findViewById(R.id.date_textview);
        changeDate=findViewById(R.id.change_date_button);

        String date_today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        dateTextView.setText(date_today);
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogue();
            }
        });



        adapter=ArrayAdapter.createFromResource(AddEditNoteActivity.this,R.array.spinner_money_type,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setSelection(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner.setSelection(0);
            }
        });

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);//to get close icon on topleft on action bar
        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTextAmount.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
            String compare=intent.getStringExtra(EXTRA_TYPE);
            spinner.setSelection(adapter.getPosition(compare));
        }
        else
        setTitle("Add Note");
    }

    private void saveNote(){
        String title = editTextAmount.getText().toString();
        String description = editTextDescription.getText().toString();
        String type = spinner.getSelectedItem().toString();
        int priority=numberPickerPriority.getValue();
        String date_set= dateTextView.getText().toString();

        int selected = spinner.getSelectedItemPosition();
        MPChartActivity mpobj;
       /* switch (selected){
            case 0:
                mpobj.

        }*/

        if(title.trim().isEmpty()|| description.trim().isEmpty()){
            Toast.makeText(this,"Please enter title and description",Toast.LENGTH_LONG).show();
            return;
        }
        Intent dataIntent= new Intent();
        dataIntent.putExtra(EXTRA_TITLE,title);
        dataIntent.putExtra(EXTRA_DESCRIPTION,description);
        dataIntent.putExtra(EXTRA_DATE,date_set);
        dataIntent.putExtra(EXTRA_PRIORITY,priority);
        dataIntent.putExtra(EXTRA_TYPE,type);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            dataIntent.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,dataIntent); //to track if task of saving note is done we use  RESULT_OK which is just an int constant
        finish();

    }

    public void showDatePickerDialogue(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
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


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        String date = dayofmonth+"/"+month+"/"+year;
        dateTextView.setText(date);
    }
}
