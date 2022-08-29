package com.bikashgurung.demo;

import android.content.Intent;
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


public class RegisterFragment extends Fragment {

    View view;
    TextView login;
    TextInputEditText username1, password1;
    MaterialButton register;
    String Susername1, Spassword1;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);


        login = view.findViewById(R.id.login);
        login.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.loginFragment));

        username1 =  view.findViewById(R.id.Rusername);
        password1 = view.findViewById(R.id.Rpassword);

        register =  view.findViewById(R.id.btnReg);
        register.setOnClickListener(view -> {
            Susername1 = username1.getText().toString();
            Spassword1 = password1.getText().toString();

            if (Susername1.isEmpty()){
                username1.requestFocus();
                username1.setError("Please Enter Name");
            }else if (Spassword1.isEmpty()){
                password1.requestFocus();
                password1.setError("Please Enter Name");
            }else if (Spassword1.length()<6){
                password1.requestFocus();
                password1.setError("Please Enter Password");
            }else {

                RegisterRequest registerRequest = new RegisterRequest(Susername1, Spassword1);

                Call<Api> call = RetrofitClient.getInstance().getApi().getPost(registerRequest);
                call.enqueue(new Callback<Api>() {
                    @Override
                    public void onResponse(Call<Api> call, Response<Api> response) {
                        if (response.isSuccessful()) {
                            Api data = response.body();
                            String token = data.getToken();

                            //Toast.makeText(MainActivity.this, data.getFirstName(), Toast.LENGTH_SHORT).show();
                            System.out.println(data.getId());
                            System.out.println(data.getToken());

                            // go to login screen with username
                            if (token != null) {
                                Navigation.findNavController(view).navigate(R.id.loginFragment);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Api> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

}