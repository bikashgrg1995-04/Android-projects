package com.bikashgurung.medcare;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    MaterialTextView email;
    TextInputEditText fullname, phoneNo, bloodType, address, status;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fullname = findViewById(R.id.etFullName);
        phoneNo = findViewById(R.id.etPhone);
        bloodType = findViewById(R.id.etBloodType);
        address = findViewById(R.id.etAddress);
        status = findViewById(R.id.etStatus);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);

        email = findViewById(R.id.etEmail);
        String emailString = getIntent().getStringExtra("email");

        if (!email.equals(""))
        {
            email.setText(emailString);
        }

        save.setOnClickListener(view -> {
            String Sname, Sphone, SbloodType, Saddress, Sstatus;

            Sname = String.valueOf(fullname.getText());
            Sphone = String.valueOf(phoneNo.getText());
            SbloodType = String.valueOf(bloodType.getText());
            Saddress = String.valueOf(address.getText());
            Sstatus = String.valueOf(status.getText());

            if (!Sname.equals("") && !Sphone.equals("") && !SbloodType.equals("") && !Saddress.equals("") && !Sstatus.equals(""))
            {
                //Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[6];
                    field[0] = "fullname";
                    field[1] = "email";
                    field[2] = "phoneno";
                    field[3] = "bloodtype";
                    field[4] = "address";
                    field[5] = "status";
                    //Creating array for data
                    String[] data = new String[6];
                    data[0] = Sname;
                    data[1] = emailString;
                    data[2] = Sphone;
                    data[3] = SbloodType;
                    data[4] = Saddress;
                    data[5] = Sstatus;
                    //Home
                    PutData putData = new PutData("http://192.168.1.10/medcare/saveDetails.php", "POST", field, data);

                    //Office
                    //PutData putData = new PutData("http://192.168.1.9/medcare/saveDetails.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();


                            if (!(result.compareTo("Details saved : Success") == 0)){
                                Toast.makeText(getApplicationContext(), "Details Saved", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    //End Write and Read data with URL
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
}