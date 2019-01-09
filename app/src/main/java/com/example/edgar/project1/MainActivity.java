package com.example.edgar.project1;

/**
 * @author Edgar Martinez-Ayala
 * MainActivity class - Class that calls second activity when first button is
 *                      clicked and then opens contacts app if legal name
 *                      is entered.
 */

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button nameButton = null;
    Button contactButton = null;

    public static final int REQUEST_CODE = 1;

    boolean isLegalName = true;    // States if the name entered is legal or not
    boolean buttonClicked = false; // States if button one has been clicked
    String legalName = null;   // Holds legal name that will be entered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameButton = findViewById(R.id.button1);
        contactButton = findViewById(R.id.button2);

        // Capture the name button clicks
        nameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Start NameActivity.class
                Intent nameIntent = new Intent(MainActivity.this,
                        NameActivity.class);
                startActivityForResult(nameIntent, REQUEST_CODE );
            }
        });

        // Opens the contacts if the second button is clicked, but
        // only if the first button has been clicked
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonClicked == true) {
                    if (isLegalName) {
                        // Opens Contacts application with name in fields
                        Intent contactsIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        contactsIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                        // Puts name into name fields
                        contactsIntent.putExtra(ContactsContract.Intents.Insert.NAME, legalName);
                        startActivity(contactsIntent);   //starts Intent
                    }
                    else {
                        Context context = getApplicationContext();
                        String msg = "Illegal Name Entered: ";
                        msg = msg + legalName;
                        int duration = Toast.LENGTH_LONG; // How long the toast should be displayed

                        // Makes and displays toast
                        Toast.makeText(context, msg, duration).show();
                    }
                }
                else{
                    Context context = getApplicationContext();
                    String msg = "Enter Name First!!!";
                    int duration = Toast.LENGTH_LONG; // How long the toast should be displayed

                    // Makes and displays toast
                    Toast.makeText(context, msg, duration).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            // Allows system to see that nameButton has been clicked
            buttonClicked = true;

            if(resultCode == RESULT_OK){
                isLegalName = true;   // Marks that name is legal
                legalName = data.getStringExtra("Name");   // Saves the name to variable

            }
            else if(resultCode == RESULT_CANCELED){
                // Checks to see if RESULT_CANCELED was sent back because
                // of the user entering the back button, thus no name entered, or
                // if it failed because it's not a legal name.
                if(data != null) {
                    isLegalName = false;   // Marks that name is not legal
                    legalName = data.getStringExtra("Name");   // Saves the name to variable
                }
                else{
                    // Sets buttonClicked to inital state since user pressed back button
                    buttonClicked = false;
                }
            }
        }
    }
}
