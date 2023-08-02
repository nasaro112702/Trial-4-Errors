package com.example.sqlitedbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etStdntID, etStdntName, etStdntProg;
    Button btAdd, btDelete, btSearch, btView;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStdntID = findViewById(R.id.etStdntID);
        etStdntName = findViewById(R.id.etStdntName);
        etStdntProg = findViewById(R.id.etStdntProg);

        etStdntID.requestFocus();

        btAdd = findViewById(R.id.btAdd);
        btDelete = findViewById(R.id.btDelete);
        btSearch = findViewById(R.id.btSearch);
        btView = findViewById(R.id.btView);

        btAdd.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        btView.setOnClickListener(this);

        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS student(stdnt_id VARCHAR, stdnt_name VARCHAR, stdnt_prog VARCHAR);");
    }

    public void clearText(){
        etStdntID.setText("");
        etStdntName.setText("");
        etStdntProg.setText("");
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onClick(View view) {
        if(view == btAdd){
            db.execSQL("INSERT INTO student VALUES('"+etStdntID.getText()+"','"+etStdntName.getText()+"','"+etStdntProg.getText()+"');");

            showMessage("Success", "Record added.");
            clearText();
        }else if(view == btDelete){
            Cursor c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='"+etStdntID.getText()+"'", null);

            if(c.moveToFirst()){
                db.execSQL("DELETE FROM student WHERE stdnt_id='"+etStdntID.getText()+"'");
                showMessage("Success", "Record Deleted.");
            }
            clearText();
        }else if(view == btSearch){
            Cursor c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='"+etStdntID.getText()+"'", null);
            StringBuffer buffer = new StringBuffer();
            if(c.moveToFirst()){
                buffer.append("Name: "+c.getString(1)+"\n");
                buffer.append("Program: "+c.getString(2)+"\n");
            }

            //Displaying all records
            showMessage("Student Details", buffer.toString());
        }else if(view == btView){

            //Retrieving all records
            Cursor c = db.rawQuery("SELECT * FROM student", null);

            //Checking if no records found
            if(c.getCount()==0){
                showMessage("Error", "No Records found.");
                return;
            }

            //Appending records to a string buffer
            StringBuffer buffer = new StringBuffer();
            while(c.moveToNext()){
                buffer.append("ID: "+c.getString(0)+"\n");
                buffer.append("Name: "+c.getString(1)+"\n");
                buffer.append("Program: "+c.getString(2)+"\n");
            }

            //Displaying all records
            showMessage("Student Details", buffer.toString());
        }
    }
}