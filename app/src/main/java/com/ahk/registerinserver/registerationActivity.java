package com.ahk.registerinserver;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class registerationActivity extends AppCompatActivity {
    EditText edtUserName,edtPassword,edtEmail,edtAddress;
    Button buRegist;
    String connectionparameters;
    byte parametresbyte[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registeration);
        edtUserName=(EditText)findViewById(R.id.edtUserName);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtAddress=(EditText)findViewById(R.id.edtAddress);

        buRegist=(Button)findViewById(R.id.buRegist);

        buRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<Void,Void,String>(){

                    @Override
                    protected void onPreExecute() {
                        String username=edtUserName.getText().toString();
                        String password=edtPassword.getText().toString();
                        String email=edtEmail.getText().toString();
                        String address=edtAddress.getText().toString();

                        String usernamekey="username=";
                        String passwordkey="&password=";
                        String emailkey="&email=";
                        String addresskey="&address=";
                        try{
                            connectionparameters=usernamekey+ URLEncoder.encode(username,"UTF-8")+passwordkey+URLEncoder.encode(password,"UTF-8")+
                                    emailkey+URLEncoder.encode(email,"UTF-8")+addresskey+URLEncoder.encode(address,"UTF-8");
                            parametresbyte=connectionparameters.getBytes("UTF-8");
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                    }



                    @Override
                    protected String doInBackground(Void... voids) {

                        try {
                            String url="http://23.96.35.34/insertuserbyPOST.php";
                            URL insertuserURL = new URL(url);
                            HttpURLConnection insertConnection=(HttpURLConnection) insertuserURL.openConnection();
                            insertConnection.setRequestMethod("POST");
                            insertConnection.getOutputStream().write(parametresbyte);
                            InputStreamReader resultStreamReader = new InputStreamReader(insertConnection.getInputStream());
                            BufferedReader resultReader = new BufferedReader(resultStreamReader);
                            return resultReader.readLine();

                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            return "error";
                        }

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        Toast.makeText(registerationActivity.this, "Registeration Succeeded", Toast.LENGTH_SHORT).show();

                    }
                }.execute();



            }
        });


    }
}
