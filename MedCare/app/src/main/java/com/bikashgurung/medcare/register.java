package com.bikashgurung.medcare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Objects;

public class register extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    TextView textViewLogin;
    MaterialButton register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextEmail = findViewById(R.id.email);
        textViewLogin = findViewById(R.id.loginText);
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(view -> {
            String username, password, email;
            username = String.valueOf(textInputEditTextUsername.getText());
            password = String.valueOf(textInputEditTextPassword.getText());
            email = String.valueOf(textInputEditTextEmail.getText());

            if (!username.equals("") && !password.equals("") && !email.equals("")){
                //Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[3];
                    field[0] = "username";
                    field[1] = "password";
                    field[2] = "email";
                    //Creating array for data
                    String[] data = new String[3];
                    data[0] = username;
                    data[1] = password;
                    data[2] = email;

                   PutData putData = new PutData("http://192.168.1.8/medcare/signup.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();


                            if (!(result.compareTo("Sign Up Success") == 0)){
                                Toast.makeText(getApplicationContext(), "Welcome.. " + result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Profile.class);
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Error" + result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    //End Write and Read data with URL
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
            }
            });
    }
}