package com.example.allandroidwidgets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckedTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    RadioGroup gender;
    RatingBar rate;

    ProgressBar progress;
    SeekBar sb;
    Switch sw;
    
    ToggleButton tb;

    AutoCompleteTextView auto_text;

    Spinner spin;

    MultiAutoCompleteTextView multi_text;
    CheckedTextView check_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gender = findViewById(R.id.rg_gender);
        rate = findViewById(R.id.rate);
        progress = findViewById(R.id.progress);
        sb = findViewById(R.id.sb_test);
        sw = findViewById(R.id.sw);
        tb = findViewById(R.id.tb_test);
        auto_text = findViewById(R.id.auto_text);
        spin = findViewById(R.id.spin_test);
        multi_text = findViewById(R.id.multi_text_test);
        check_text = findViewById(R.id.checkText);
        

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rd_male:
                        Toast.makeText(MainActivity.this, "Male is clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rd_female:
                        Toast.makeText(MainActivity.this, "Female is clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(MainActivity.this, "Progress: "+progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        String names[] = {
                "Ryan Elico",
                "Nessa Manlapaz",
                "Ian Elico"
        };

        ArrayAdapter<String> name_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        auto_text.setAdapter(name_adapter);

        ArrayAdapter<String> spin_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);

        spin.setAdapter(spin_adapter);
        
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spin.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Item: "+name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        multi_text.setAdapter(name_adapter);
        multi_text.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        check_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_text.toggle();
                Toast.makeText(MainActivity.this, "Checked: "+check_text.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    

    
    public void clickToggle(View v){
        if(tb.isChecked()){
            Toast.makeText(this, "Toggled", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not toggled", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void clickSwitch(View v){
        if(sw.isChecked()){
            Toast.makeText(this, "Switched", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not switched", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void startProgress(View v){
        Thread th = new Thread(){
            @Override
            public void run(){
                super.run();
                for(int x = 0; x <= 100; x = x = x+2){
                    try{
                        sleep(50);
                        progress.setProgress(x);

                    }catch(Exception e){}
                }
                progress.setProgress(0);
            }
        };
        th.start();
    }

    public void showRate(View v){
        float ratings = rate.getRating();

        Toast.makeText(this, "Ratings is: "+ratings, Toast.LENGTH_SHORT).show();
    }

    public void clearGenderSelection(View v){
        gender.clearCheck();
    }

}