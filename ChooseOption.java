package com.example.kaila.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ChooseOption extends AppCompatActivity {
    // This is the array of string for the array adapter.
    String [] choose = {"UDP Server", "UDP Client", "TCP Server", "TCP Client"};
    // This is the declaration of the submit button.
    Button submit;
    // This is the declaration for port edit text and ip edit text.
    EditText portET, ipET;
    // This is the declaration of the spinner.
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This sets the content view activity_choose_option.
        setContentView(R.layout.activity_choose_option);
        //This finds the toolbar as a view and sets the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This is the veiw of the floating action button.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar with a toast appears as this button is clicked.
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // This is the declaration of the array adapter and the array adapter is set to simplt_spinner_item.
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, choose);
        // Dropdown view resource is set for the array adapter.
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // This gets the editText view reference
        ipET = (EditText) findViewById(R.id.ipEditText);
        // This gets the portET view reference.
        portET = (EditText) findViewById(R.id.portEditText);

        // This gets the spinner view as a spinner.
        spinner = (Spinner) findViewById(R.id.chooseSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(arrayAdapter);

        // This gets the ip address as a final string.
        final String ip_address = ipET.getText().toString();
        // This gets port_no as a final string.
        final String port_no = portET.getText().toString();
        // This gets the string from the spinner.
        final String chooseServer = spinner.toString();

        // This gets the reference of the submit button.
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to move from one activity to another.
                Intent intent = new Intent(ChooseOption.this, Conversation.class);
                // Put ip address, port numbere and chooseServer.
                intent.putExtra("IP_ADDRESS", ip_address);
                intent.putExtra("PORT_NO", port_no);
                intent.putExtra("SERVER", chooseServer);

                // This start another activity form the intent.
                ChooseOption.this.startActivity(intent);
            }
        });
    }



}
