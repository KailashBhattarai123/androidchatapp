package com.example.kaila.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    // Declaration of the text view username and password.
    private TextView usernameTextView, passwordTextView;
    // Declaration of the edit text username and password.
    private EditText usernameEditText, passwordEditText;
    //Declaration of the login button.
    private Button loginButton, signupButton;
    //Declaration of strings username, password and theString.
    String username, password;
    String theString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the layout to activity_main.
        setContentView(R.layout.activity_main);
        // Find the view for the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the toolbar.
        setSupportActionBar(toolbar);

        //Finds the views for the signup button.
        signupButton = (Button) findViewById(R.id.signupBut);

        // Finds the views for the login button.
        loginButton = (Button) findViewById(R.id.loginBut);
        //This sets the edittext as usernameEditText.
        usernameEditText = (EditText) findViewById(R.id.unameET);
        // This  sets the passwordEditText. It finds the Views.
        passwordEditText = (EditText) findViewById(R.id.passwordET);
        // This sets the username string by getting the content from the edit text.
        username = usernameEditText.getText().toString();
        //Gets the string from the passwordEditText and set it to string.
        password = passwordEditText.getText().toString();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This change the activity here.
                Intent intent = new Intent(MainActivity.this, Signup.class);
                //This starts the intent.
                MainActivity.this.startActivity(intent);
            }
        });

        // When loginButton is clicked.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //try catch is for exception handling.
                try {
                    // gets all the data from the php. username and password are sent as a GET method.
                    theString = content("http://127.0.0.1/android/username.php?uname="+username+"&password="+password+"");
                } catch (MalformedURLException e) {
                    // When there is exception this is displayed.
                    System.out.println("not working.");
                }

                // If string is gets the result.
                if (theString.equals(username)) {
                    //This change the activity here.
                    Intent intent = new Intent(MainActivity.this, ChooseOption.class);
                    //This starts the intent.
                    MainActivity.this.startActivity(intent);
                }
                else{
                    
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

        // This method returns a string.
    public String content(String link_url) throws MalformedURLException {
        String thestring = "";

        // This is for the exception handling.
        try {
            //This sets the url. New URL is set here.
            URL url = new URL(link_url);
            // URLConnection is established as url connection is opened.
            URLConnection connection = url.openConnection();
            // URL is converted into HTTPURL.
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) connection;
            //Response code is gotten.
            int resCode = httpURLConnection.getResponseCode();
            //Length of the http is gotten.
            int length = httpURLConnection.getContentLength();

            //If res code is HTTP_OK then this condition is satisfied.
            if (resCode == HttpsURLConnection.HTTP_OK)
            {
                // This input stream gets the input from the httpURLConnection.
                InputStream input = httpURLConnection.getInputStream();
                // This reads the input stream.
                InputStreamReader inputStreamReader = new InputStreamReader(input);
                //This bufferes the input stream reader.
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                // This is the loop until the length is less than the no of lines.
                do {
                    thestring = thestring + bufferedReader.readLine() + "\n";
                }
                while(thestring.length() < length);

                // This closes the input stream.
                input.close();
            }
            else{
                System.out.print("Not cool.");
            }


        }
        catch (Exception e){

        }

        return thestring;
    }


}
