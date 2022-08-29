package com.bikashgurung.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    MaterialTextView showUsername;
    MaterialButton logout;
    String id, username, password;


    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        String tokenValue = preferences.getString("userToken", "");

        showUsername = findViewById(R.id.txt2);

        if (tokenValue != null) {
            showUsername.setText(tokenValue);
        }

        logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogoutRequest logoutRequest = new LogoutRequest(username, password);
                Call<Api> call = RetrofitClient.getInstance().getApi().getPost(logoutRequest);

                call.enqueue(new Callback<Api>() {
                    @Override
                    public void onResponse(Call<Api> call, Response<Api> response) {
                        Api data = response.body();
                        //Toast.makeText(MainActivity.this, data.getFirstName(), Toast.LENGTH_SHORT).show();
                        System.out.println(data.getToken());
                        if (data.getToken() == null) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.remove("logged");
                            editor.commit();

                            Intent intent = new Intent(Profile.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(Profile.this, "Logout Success.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Api> call, Throwable t) {
                        Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}