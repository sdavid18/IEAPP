package com.example.sdavi.ieapp.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by sdavi on 2017. 12. 03..
 */

public class InputValidation {
    private Context context;

    public InputValidation(Context context){
        this.context = context;
    }

    public boolean isInputEditTextFilled(EditText textInputEditText, LinearLayout textInputLayout, String message,Context context){
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            hideKeyboardFrom(textInputEditText);
            return false;
        }
        return true;
    }

    public boolean isInputEditTextEmail(EditText textInputEditText, LinearLayout textInputLayout, String message,Context context){
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            hideKeyboardFrom(textInputEditText);
            return false;

        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText textInputEditText1, EditText textInputEditText2, LinearLayout textInputLayout, String message,Context context ){
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            hideKeyboardFrom(textInputEditText2);
            return false;
        }
        return true;
    }

    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
