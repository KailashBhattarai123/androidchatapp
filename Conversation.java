package com.example.kaila.chatapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Conversation extends AppCompatActivity {
    // Declaration of the socket socketSender.
    public Socket socketSender;
    // Declaration of the datagram socket datagramSocket.
    DatagramSocket datagramSocket;
    // Declaration of the BufferedReader,
    public BufferedReader bufferedReader;
    // Declaration of the PrintStream.
    public PrintStream printStream;

    //Declaration of the TextView msgTextView.
    TextView msgTextView;
    // Declaration of the EditText sendEditTest.
    EditText sendEditText;
    //Declaration of the Button for the sendButton, saveButton, and displayButton.
    Button sendButton, saveButton, displayButton;

    // Declaration of the string portNo, ip_address, choose and username.
    String portNo;
    String ip_address;
    String choose;
    String username="";

    //This is the UDPListener that implements Runnable.
    class UDPListener implements Runnable
    {
        //Declaration of the String strings.
        String strings;
        // This method is called to run.
        public void run()
        {
            //Declaration of the DatagramPacket datagramPacket.
            DatagramPacket datagramPacket;
            //Array of the byte buffer.
            byte[] buffer = new byte[256];
            //Try catch method is implemented so that the program doesn't get interrupted by the exception.
            try {
                // While the statement is true loop is executed.
                while(true)
                {
                    // datagramPacket is instantiated with buffer and the length.
                    datagramPacket = new DatagramPacket(buffer, buffer.length);
                    //datagramSocket receives the datagramPacket.
                    datagramSocket.receive(datagramPacket);

                    // Declaration of the string that gets data from the datagram packet.
                    String string = new String(datagramPacket.getData());

                    // Sequence of characters charSequence gets the text from the msgTextView.
                    CharSequence charSequence = msgTextView.getText();
                    // Gets the sequence of characters and adds it to the strings.
                    strings = charSequence + "" + string + "\n";
                    msgTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            msgTextView.setText(strings);
                        }
                    });

                }
                // This is to catch the exception.
            } catch (IOException e) {
                // This gets the message from the exception.
                e.getMessage();
            }
        }
    }

    // This is the class that implements runnable.
    class TCPListener implements Runnable
    {
        // Declaration of the string.
        String strings;
        //Method run that runs constantly.
        public void run(){
            // Exception handling.
            try{
                // Declares the socket with ip_address and the port.
                socketSender = new Socket(ip_address, Integer.valueOf(portNo));
                // Declaration of the bufferedReader that gets the the input stream from the socetSender.
                bufferedReader = new BufferedReader(new InputStreamReader(socketSender.getInputStream()));
                // Inititalization of the printStream that gets the outputStream form the printStream.
                printStream = new PrintStream(socketSender.getOutputStream());
                // This shows that the link between server and client has been established.
                printStream.println("");
                // While it is true.
                while(true)
                {
                    // string that reads from the bufferedReader's readLine.
                    String string = bufferedReader.readLine();
                    // CharSequence charsequence getting sequence of characters from msgTextView.
                    CharSequence charSequence = msgTextView.getText();
                    // Adds the string to hte previous charsequence.
                    strings = charSequence + "\r\n" + string;

                    msgTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            msgTextView.setText(strings);
                        }
                    });
                }
            }
            // Cathes all the types of exception.
            catch (Exception e) {
                // Log file that gets the message if there is an exception.
                Log.i(getClass().getName(), "There is an exception.");
            }
        }
    }

    // This method save all the data in file.
    public void saveInFile() {
        // Get the text, convert it into the string.
        String msgString = msgTextView.getText().toString();

        // initialize the FileOutputStream fileOutputStream.
        FileOutputStream fileOutputStream;

        // Exception handling.
        try {
            // Opens the file output to save in file message.txt. The context mode is private.
            fileOutputStream = openFileOutput("message.txt", Context.MODE_PRIVATE);
            // Write in the file with the bytes and the certain length.
            fileOutputStream.write(msgString.getBytes(), 0, msgString.length());
            // Closes the file output stream.
            fileOutputStream.close();
        }
        // If there is an exception file not found for exception.
        catch (FileNotFoundException e){
            // gets the message of the exception.
            e.getMessage();
        }
        // This catches the exception. an input output exception.
        catch (IOException e) {
            // This gets the message for the exception.
            e.getMessage();
        }
    }

    // This method gets the data from the file.
    public void getFromFile(){
        // Sets the buffer array here.
        byte[] buffer = new byte[256];
        // Sets the string as a null value.
        String string = "";
        // initialize the integer.
        int integer = 0;

        // Declaration of the input stream fileInputStream.
        FileInputStream fileInputStream;
        // Exception handling to handle the exception.
        try{
            // Opens the input file for the message.txt file.
            fileInputStream = openFileInput("message.txt");

            // The do while loop to get all the strings of the file.
            do {
                // This reads the input stream from the buffer.
                integer = fileInputStream.read(buffer, 0, buffer.length);

                // If there is the end of the file
                if (integer != -1)
                {
                    // Then the buffer ends.
                    string = string + new String(buffer, 0, integer);
                }
            }
            while ((integer != -1));
            // file input stream is closed.
            fileInputStream.close();

            // Sets the text to the msgTextView.
            msgTextView.setText(string);
        }
        // If the file is not found this catch function is triggered.
        catch (FileNotFoundException e) {
            // Gets the message from the exception.
            e.getMessage();
        }
        // If there is an input output exception.
        catch (IOException e)
        {
            // Gets the message from the exception.
            e.getMessage();
        }
    }


    // This is triggered as soon as the activity is opened.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This gets the activity activity_conversation.
        setContentView(R.layout.activity_conversation);
        // This finds the toolbar as a view.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // This sets the toolbar as the view.
        setSupportActionBar(toolbar);

        // Send button to get the reference of the view.
        sendButton = (Button) findViewById(R.id.sendButton);
        // Save button to get the reference of the veiw.
        saveButton = (Button) findViewById(R.id.saveButton);
        // msgTextView is the text view that gets the view id for messageTextView.
        msgTextView = (TextView) findViewById(R.id.messageTextView);
        // This sets the EditText from the view sendEditText.
        sendEditText = (EditText) findViewById(R.id.sendEditText);
        // This displayButton gets the view and convert it into the button.
        displayButton = (Button) findViewById(R.id.displayButton);

        // This gets the portNo from the previous activity.
        portNo = getIntent().getStringExtra("PORT_NO");
        // This gets the ip_address from the previous activity.
        ip_address = getIntent().getStringExtra("IP_ADDRESS");
        // This gets the string from the server.
        choose = getIntent().getStringExtra("SERVER");


        msgTextView.setText("");

        // As send button is clicked this method is triggered.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This activity is run in separate thread.
                new Thread() {
                    // run method code contais everything that must be run in a repeated time frame.
                    public void run() {
                        // this gets the string from the sendEditText.
                        String string = sendEditText.getText().toString();
                        // Print string in the printStream.
                        printStream.println(string);
                    }
                }.start();
            }
        });

        if (choose.equals("TCP Server") && choose.equals("TCP Client")) {
            // Initialize the thread for the object of TCPListener.
            Thread thread = new Thread(new TCPListener());
            // starts the thread.
            thread.start();
        }





        try{
            datagramSocket = new DatagramSocket();
        }
        catch (SocketException e){}

        // When send button is clicked this will occur.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // string that gets the text from the send edit text.
                String string = sendEditText.getText().toString();
                // Tjos sets  the messgae in the textView.
                msgTextView.setText(msgTextView.getText() + username +"\n>"+string+"\r\n");
                // Exception handling .
                try {
                    // Array that buffers.
                    byte[] buffer = new byte[256];
                    // Gets the bytes from the starig.
                    buffer = string.getBytes();

                    // Gets the address of the ip.
                    InetAddress address = InetAddress.getByName("" + ip_address);
                    // This is the datagramPacket that gets the buffer, buffer length, ip address and the intger porto.
                    final DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, address, Integer.valueOf(portNo));

                    new Thread()
                    {
                        public void run() {
                            try {
                                // Sends the datagramPacket through socket.
                                datagramSocket.send(datagramPacket);
                            }
                            // This catch the input output exception.
                            catch (IOException e) {
                                // Gets the message for the exception.
                                e.getMessage();
                            }
                        }
                    }.start();
                }
                catch (UnknownHostException exception){
                    exception.printStackTrace();
                }
            }
        });

        if (choose.equals("TCP Server") && choose.equals("TCP Client")) {
            // Initialize the thread for UDP Server.
            Thread t = new Thread(new UDPListener());
            // Starts the thread.
            t.start();

        }



        // If saveButton is clicked the file in the message text view gets saved.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInFile();
            }
        });

        // If displayButton is pressed the file save in message text view gets displayed.
        displayButton.setOnClickListener(new View.OnClickListener()
                              {
                                  public void onClick (View view)
                                  {
                                      getFromFile();

                                  }
                              }
        );
    }

}
