package com.example.sdavi.ieapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sdavi.ieapp.helper.InputValidation;
import com.example.sdavi.ieapp.sql.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText xtext;
    CheckBox xcheck;
    private final AppCompatActivity activity = MainActivity.this;
    private NestedScrollView nestedScrollView;
    private LinearLayout textInputLayoutEmail;
    private LinearLayout textInputLayoutPassword;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private Button appCompatButtonLogin;
    private TextView textViewLinkRegister;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        //checkbox show/hide password
        xtext = (EditText) findViewById(R.id.textInputEditTextPassword);
        xcheck = (CheckBox) findViewById(R.id.cbpass);

        xcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (!ischecked) {
                    xtext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    xtext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        initViews();
        initListeners();
        initObjects();
    }
    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.NestedScrollView);

        textInputLayoutEmail = (LinearLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (LinearLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin= (Button) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (TextView) findViewById(R.id.textViewLinkRegister);
    }

    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email), this)) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email),this)) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password),this)) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(activity, LoginActivity.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
