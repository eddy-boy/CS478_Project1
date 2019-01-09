package com.example.edgar.project1;

/**
 * @author Edgar Martinez-Ayala
 * NameActivity class - Class thats called from main activity when first button is
 *                      clicked and checks the name that the user entered to see
 *                      if it's a legal name.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        editText2 = findViewById(R.id.editText2);

        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String name;
                Intent sendBack = new Intent();

                name = editText2.getText().toString();    // Gets text from editText and converts it to string
                name = name.trim();    // Removes leading and trailing whitespaces
                name = name.replaceAll(" +", " "); // Replaces multiple spaces into one

                if(isLegalName(name)){
                    // Send the activity back
                    sendBack.putExtra("Name" , name);
                    setResult(RESULT_OK, sendBack);
                }
                else{
                    sendBack.putExtra("Name" , name);
                    setResult(RESULT_CANCELED, sendBack);
                }
                finish();  //closes the activity
                return false;
            }
        });
    }

    // Checks to see if the name entered by the user us a
    // legal name.
    private boolean isLegalName(String name){
        // Checks to see that name isn't empty, has no numbers, and
        // that there is a space
        if(name == null || name.isEmpty() || name.matches(".*\\d+.*")){
            return false;
        }
        // Contains a space and is all letters thus it's a legal name
        if (name.contains(" ")){
            return true;
        }
        return  false;
    }
}

