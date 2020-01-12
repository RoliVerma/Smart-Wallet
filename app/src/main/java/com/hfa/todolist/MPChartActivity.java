package com.hfa.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.app.Application;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MPChartActivity extends AppCompatActivity {
    //public int countRecv, countSpent, countBorrowed, countLent;
    static PieChart pieChart;
    private NoteDao noteDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart);

        pieChart = findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        ChartData chartData;

        NoteDatabase database = NoteDatabase.getInstance(getApplication());
        noteDao = database.noteDao();
        new CountAnalysisAsyncTask(noteDao).execute();


}

    public static class CountAnalysisAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private ChartData chartData;


        public CountAnalysisAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
            this.chartData= new ChartData();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            chartData.setCountBorrowed(noteDao.countBorrowed());
            chartData.setCountLent(noteDao.countLent());
            chartData.setCountRecv(noteDao.countSpent());
            chartData.setCountSpent(noteDao.countSpent());

            return null;
        }

        @Override
        protected void onPreExecute() {
            chartData=new ChartData();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //cant create an obj of activity class
           // chartData.setChartData(chartData);

            ArrayList<Entry> yvalues = new ArrayList<Entry>();
            yvalues.add(new Entry(chartData.countRecv, 0));
            yvalues.add(new Entry(chartData.countSpent, 1));
            yvalues.add(new Entry(chartData.countBorrowed, 2));
            yvalues.add(new Entry(chartData.countLent, 3));


            PieDataSet dataSet = new PieDataSet(yvalues, "Expenditure type");

            ArrayList<String> xvalues = new ArrayList<String>();
            xvalues.add("Received");
            xvalues.add("Spent");
            xvalues.add("Borrowed");
            xvalues.add("Lent");
            PieData data = new PieData(xvalues, dataSet);

            data.setValueFormatter(new DefaultValueFormatter(0));
            pieChart.setData(data);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(50f);
            pieChart.setHoleRadius(30f);

            dataSet.setValueTextSize(14f);
            dataSet.setValueTextColor(Color.DKGRAY);

        }



    }



}
