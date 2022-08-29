package com.bikashgurung.medcare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends Fragment {

    View view;
    MaterialTextView registerText;
    TextInputEditText textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    MaterialButton login;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        textInputEditTextUsername = view.findViewById(R.id.loginUsername);
        textInputEditTextPassword = view.findViewById(R.id.loginPassword);
        textInputEditTextEmail = view.findViewById(R.id.loginEmail);
        login = view.findViewById(R.id.btnLogin);
        login.setOnClickListener(view -> {
            String username, password, email;
            username = String.valueOf(textInputEditTextUsername.getText());
            password = String.valueOf(textInputEditTextPassword.getText());
            email = String.valueOf(textInputEditTextEmail.getText());

            if (!username.equals("") && !password.equals("")){
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

                    PutData putData = new PutData("http://192.168.1.8/medcare/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();

                            if (result.equals("Login Success")){
                                Toast.makeText(getContext(), "Welcome.. " + result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Profile.class);
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);
                                startActivity(intent);

                            }else {
                                Toast.makeText(getContext(), "Sorry" + result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    //End Write and Read data with URL
                });
            }
            else {
                Toast.makeText(getContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
            }
        });

        registerText = view.findViewById(R.id.textRegister);
        registerText.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), register.class);
            startActivity(intent);
        });

        return view;
    }
}