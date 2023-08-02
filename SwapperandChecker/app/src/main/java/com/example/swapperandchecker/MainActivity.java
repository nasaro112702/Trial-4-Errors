package com.example.swapperandchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et_1, et_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
    }

    public void swapWord(View view){
        String first_word = et_1.getText().toString();
        String second_word = et_2.getText().toString();

        et_1.setText(second_word);
        et_2.setText(first_word);
    }

    public void checkSimilarity(View view){
        String first_word = et_1.getText().toString();
        String second_word = et_2.getText().toString();
        String remarks = "";

        if(first_word.equals(second_word)){
            remarks = "SAME";
        }else{
            remarks = "NOT THE SAME";
        }

        Intent intent = new Intent(this, MyMessage.class);
        intent.putExtra("remarks", remarks);
        startActivity(intent);
    }
}