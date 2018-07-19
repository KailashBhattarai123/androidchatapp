package com.example.kaila.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Signup extends AppCompatActivity {
    //Edit text for name, email, username, password and confirm password.
    EditText nameEditText, emailEditText, usernameEditText, passwordEditText, confpasswordEditText;
    // Declaration of the button signupButton.
    Button signupButton;
    // initialization of strings name, email, username, password and confirm password.
    String name, email, username, password, confpassword, theString;



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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // this gets the view nameEditText
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        // this gets the view emailEditText
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        // this gets the view usernameEditText
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        // this gets the view passwordEditText
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        // this gets the view confpasswordEditText
        confpasswordEditText = (EditText) findViewById(R.id.confpasswordEditText);
        // gets the button signupButton.
        signupButton = (Button) findViewById(R.id.signup);

        name = nameEditText.getText().toString();
        email = emailEditText.getText().toString();
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confpassword = confpasswordEditText.getText().toString();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (password.equals(confpassword)) {
                    //try catch is for exception handling.
                    try {
                        // gets all the data from the php. username and password are sent as a GET method.
                        theString = content("http://127.0.0.1/android/signup.php?name =" + name + "&email=" + email + "&uname=" + username + "&password=" + password + "");
                    } catch (MalformedURLException e) {
                        // When there is exception this is displayed.
                        System.out.println("not working.");
                    }

                    // If string is gets the result.
                    if (theString.equals("")) {
                        //This change the activity here.
                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        //This starts the intent

                        Signup.this.startActivity(intent);
                    } else {

                    }
                }
                else {
                    System.out.print("Password and confirm password do not match.");
                }
            }
        });

    }
}
