package com.example.studentcomobile2019;


import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private String User;
    private String Password;
    private int nbrTentatives = 0;
    private boolean Authentif = true;
    String connectionResult;
    RESTClient restClient = new RESTClient();

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
                connectionResult = restClient.callWebservice("/studentCo_rol_jquerry_17_oct/login/" + User + "/" + Password);

                // Vérification que les deux champs user et mot de passe son setés
                if(!User.toString().equals("")  && !Password.toString().equals("")){
                    // Il faut que la réponse du WS soit true + Anthentif = true (valeur initiale). Si pas true => on ne sait plus rien faire.
                    if (connectionResult.equals("true") && Authentif == true) {
                        this.nbrTentatives = 0;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginOK), Toast.LENGTH_LONG).show();
                        Authentif = true;

                    }
                    // Tant qu'on ne satifait pas à la connexion, on incrémente le nombre d'essai
                    else if(nbrTentatives  < 2){
                        nbrTentatives++;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry), Toast.LENGTH_LONG).show();
                    }
                    // Si on a dépassé le nombre d'essai => Authentif = false et là c'est foutu.
                    else {
                        Authentif = false;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.gloup), Toast.LENGTH_LONG).show();
                    }

                    break;
                }
                // Cas où l'utilisateur n'a rien encodé dans le champs utilisateur
                else if ( User.toString().equals("")){
                    EditText  etUsername =  findViewById(R.id.leUsername);
                    etUsername.setError(getResources().getString(R.string.PasDeUsername));

                }
                // Cas où l'utilisateur n'a rien encodé dans le champs mot de passe
                else if(Password.toString().equals("")){
                    EditText  etPassword =  findViewById(R.id.lePassword);
                    etPassword.setError(getResources().getString(R.string.PasDePassword));
                }
        }
    }


}
