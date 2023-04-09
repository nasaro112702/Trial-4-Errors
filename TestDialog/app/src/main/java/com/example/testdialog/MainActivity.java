package com.example.testdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView name;
    EditText dialog_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
    }

    public void openDialog(View view){
        View dialog = getLayoutInflater().inflate(R.layout.layout_dialog, null);

        dialog_name = dialog.findViewById(R.id.dialog_name);

        new AlertDialog.Builder(dialog.getContext())
                .setView(dialog)
                .setTitle("Test")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name.setText(dialog_name.getText().toString());
                    }
                }).create().show();
    }


}