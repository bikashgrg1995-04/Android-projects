package com.bikashgurung.demo;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    View view;
    TextView register, forgot_password;
    TextInputEditText username, password;
    MaterialButton login;
    String Susername, Spassword;

    public static final String PREFS_NAME = "LoginPrefs";


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Check if we successfully logged in before.
         * If we did, redirect to home page
         */
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(getActivity(), Profile.class);
            startActivity(intent);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        register = (TextView) view.findViewById(R.id.txtRegister);
        register.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.registerFragment));

        forgot_password = (TextView) view.findViewById(R.id.forgot);
        forgot_password.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.forgotPassword));

        username = (TextInputEditText) view.findViewById(R.id.username);
        password = (TextInputEditText) view.findViewById(R.id.password);

        login = (MaterialButton) view.findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Susername = username.getText().toString();
                Spassword = password.getText().toString();

                if (Susername.isEmpty()) {
                    username.requestFocus();
                    username.setError("Please Enter Username");
                } else if (Spassword.isEmpty()) {
                    password.requestFocus();
                    password.setError("Please Enter Name");
                } else if (Spassword.length() < 6) {
                    password.requestFocus();
                    password.setError("Please Enter Password");
                } else {

                    LoginRequest loginRequest = new LoginRequest(Susername, Spassword);

                    Call<Api> call = RetrofitClient.getInstance().getApi().getPost(loginRequest);
                    call.enqueue(new Callback<Api>() {
                        @Override
                        public void onResponse(Call<Api> call, Response<Api> response) {
                            if (response.isSuccessful()) {
                                Api data = response.body();
                                String token = data.getToken();
                                //Toast.makeText(MainActivity.this, data.getFirstName(), Toast.LENGTH_SHORT).show();

                                if (token != null) {
                                    System.out.println(token);

                                    //make SharedPreferences object
                                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("logged", "logged");
                                    editor.putString("userToken", token);
                                    editor.commit();

                                    Intent intent = new Intent(getActivity(), Profile.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    if (getActivity() != null) {
                                        getActivity().finish();
                                    }
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<Api> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        return view;
    }

}