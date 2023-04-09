package com.example.customdialog2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener {

    TextView tv_username, tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_username = findViewById(R.id.tv_username);
        tv_password = findViewById(R.id.tv_password);

    }

    public void openDialog(View view){
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void passValue(String username, String password) {
        tv_username.setText(username);
        tv_password.setText(password);
    }
}