package com.cda.smu.satishpanigrahi.weightwatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    Button btnSignIn,btnSignUp, btnReset;
    LoginDataBaseAdapter loginDataBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create instance of SQlite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Get The Reference Of Buttons
        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);
        btnReset = (Button)findViewById(R.id.buttonReset);


        // Set onClick listener on SignUP button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create indent for SignupActivity and start the activity
               Intent intentSignUp = new Intent(getApplicationContext(),SignUpActivity.class);
               startActivity(intentSignUp);


            }
        });
    }

    // method to click event of Sign in button
    public void signIn(View V){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Login");

        // get the Refferences of views
        final EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword))
                {

                    Toast.makeText(MainActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    dialog.dismiss();


                    //stored login value in shared preferences
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", userName);

                    Intent intentBMI1 = new Intent(MainActivity.this, BMIActivity.class);
                    intentBMI1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentBMI1);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }

        });
        dialog.show();

    }


    // Method to delete the login.db
    public void resetDB(View V){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_delete_db);
        dialog.setTitle("Reset Database");



        // get the Refferences of views
        final EditText editTextUserName1=(EditText)dialog.findViewById(R.id.editTextNameToDelete);
        final  EditText editTextPassword1=(EditText)dialog.findViewById(R.id.editTextPasswordToDelete);

        Button btnDel =(Button)dialog.findViewById(R.id.buttonDeleteDB);

        btnDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get The User name and Password
                String userName = editTextUserName1.getText().toString();
                String password = editTextPassword1.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword)) {
                    loginDataBaseAdapter.deleteEntry(userName);   // delete from login table

                    Toast.makeText(MainActivity.this, "Deleted entries for user name", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }

        });
        dialog.show();

    }

    public void viewRecord(View V){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_view);
        dialog.setTitle("View record");



        // get the Refferences of views
        final EditText editTextUserName2=(EditText)dialog.findViewById(R.id.editTextUserNameToView);
        final  EditText editTextPassword2=(EditText)dialog.findViewById(R.id.editTextPasswordToView);

        Button btnView =(Button)dialog.findViewById(R.id.buttonToViewRecord);

        btnView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get The User name and Password
                String userName = editTextUserName2.getText().toString();
                String password = editTextPassword2.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword)) {
                    // Show the entries here

//                    Cursor res = loginDataBaseAdapter.getAllData();
//                    if(res.getCount() == 0){
//                        Toast.makeText(MainActivity.this, "No record", Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    }
//                    else{
//                        StringBuffer buffer = new StringBuffer();
//                        while(res.moveToNext()){
//                            buffer.append("HealthCardNo: " +res.getString(0) +"\n");
//                            buffer.append("Height: " +res.getString(1) +"\n");
//                            buffer.append("Weight: " +res.getString(2) +"\n \n");
//                        }
//
//                        // Show all data
//                       // showmessage("Record", buffer.toString());
//                       // Toast.makeText(MainActivity.this, "Need to show buffer", Toast.LENGTH_LONG).show();
//
//                    }
                    Toast.makeText(MainActivity.this, "Error in this part - not able to retrive data", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }





            }

        });


        dialog.show();

    }

    public void showmessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }




}
