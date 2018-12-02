package com.example.cesaraugusto.poker;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;


public class Activity_Login extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter;
    private Button userRegistration;
    private FirebaseAuth mAuth;
    private static final String TAG = "Activity_Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);

        Username = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();


        userRegistration = (Button) findViewById(R.id.btnSingIn) ;
        Info.setText("Numero de intentos incorrectos: 5");

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                validateF(Username.getText().toString(), Password.getText().toString());


            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_Login.this, Activity_Registrar.class));
            }
        });

    }


    private void validateF(String userName, String userPassword) {

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            Toast.makeText(Activity_Login.this, "INGRESE VALORES", Toast.LENGTH_SHORT).show();
        }
        else{
        mAuth.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Toast.makeText(Activity_Login.this, "BIENVENIDO", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Activity_Login.this, MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Activity_Login.this, Activity_Login.class));
                            //updateUI(null);
                        }

                        // ...
                    }
                });
        }

    }

    private void validate(String userName, String userPassword){
        if ((userName == "Admin") && (userPassword == "1234")){

            Intent intent = new Intent(Activity_Login.this, Activity_Principal.class);
            startActivity(intent);
        }else{
            counter--;
            Info.setText("Numero de intentos incorrectos: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);

            }
        }

    }
}
