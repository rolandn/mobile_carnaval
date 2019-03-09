package com.example.studentcomobile2019;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String User;
    private String Password;
    private int nbrTentatives = 0;
    private boolean TryEncode = true;
    String connectionResult;
    RESTClient restClient = new RESTClient();
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnLogin(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                User = ((EditText) findViewById(R.id.leUsername)).getText().toString();
                Password = ((EditText) findViewById(R.id.lePassword)).getText().toString();
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().build());
                connectionResult = restClient.callWebservice("/studentCo_rol_jquerry_17_oct/#/login/" + User + "/" + Password + "/");

                if(!User.toString().equals("")  && !Password.toString().equals("")){
                    if (!connectionResult.equals("null") && TryEncode == true) {
                        this.nbrTentatives = 0;
                        TryEncode = true;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginOK), Toast.LENGTH_LONG).show();


                    }
                    else if(nbrTentatives  < 2){
                        nbrTentatives++;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry), Toast.LENGTH_LONG).show();
                    }
                    else {
                        TryEncode = false;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.gloup), Toast.LENGTH_LONG).show();
                    }

                    break;
                }
                else if ( User.toString().equals("")){
                    EditText  etUsername =  findViewById(R.id.leUsername);
                    etUsername.setError(getResources().getString(R.string.PasDeUsername));

                }
                else if(Password.toString().equals("")){
                    EditText  etPassword =  findViewById(R.id.lePassword);
                    etPassword.setError(getResources().getString(R.string.PasDePassword));
                }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.mainMenu_initLogin){
            this.nbrTentatives = 0;
            this.TryEncode = true;
            Toast.makeText(getApplicationContext(), "Authentification réinitialisée", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.mainMenu_quit){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
