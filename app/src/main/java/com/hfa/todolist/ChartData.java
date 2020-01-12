package com.hfa.todolist;

import android.app.Application;

public class ChartData {
    public int countRecv, countSpent, countBorrowed, countLent;
    //NoteDao noteDao;


    public void setCountRecv(int c){countRecv=c;}
    public void setCountLent(int c){countLent=c;}
    public void setCountBorrowed(int c){countBorrowed=c;}
    public void setCountSpent(int c){countSpent=c;}

    public void setChartData(ChartData chartData){
        ChartData c = new ChartData();
        c.countRecv=chartData.countRecv;
        c.countBorrowed=chartData.countBorrowed;
        c.countLent=chartData.countRecv;
        c.countSpent=chartData.countSpent;

    }

}
