package com.bikashgurung.stocard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.DBModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class Get_Card_Details extends AppCompatActivity {

    ImageView imageView;
    TextView fullKeyboard, numberKeyboard, scanBarCode;
    TextInputEditText cardNo, description;
    MaterialButton ok, cancel;
    String CardNo, cardName, cardImage;
    TextInputLayout descriptionLayout;

    String final_cardNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_card_details);

        imageView = findViewById(R.id.scanCardImg);
        fullKeyboard = findViewById(R.id.tvFullKeyboard);
        scanBarCode = findViewById(R.id.tvScanBarCode);
        numberKeyboard = findViewById(R.id.tvNumericKeyboard);
        ok = findViewById(R.id.btnDone);
        cancel = findViewById(R.id.btnCancel);
        cardNo = findViewById(R.id.cardNo);
        description = findViewById(R.id.description);
        descriptionLayout = findViewById(R.id.outlinedTextDescription);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            //Extract the data…
            CardNo = bundle.getString("cardNo");
            cardName = bundle.getString("cardName");
            cardImage = bundle.getString("cardImage");

            // Picasso.get().load(cardImage).into(imageView);

            imageView.setImageDrawable(AppCompatResources.getDrawable(this, Integer.parseInt(cardImage)));
            cardNo.setText(CardNo);
        }

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<DBModel> cardList = db.cardDao().getAll();
        int count = 0;
        if (cardList != null) {
            for (int i = 0; i < cardList.size(); i++) {
                String DB_cardName = cardList.get(i).cardName;
                if (DB_cardName.equals(cardName)) {
                    System.out.println(i);
                    count++;

                }
            }
        }


        if (count > 0) {
            descriptionLayout.setVisibility(View.VISIBLE);
            //description.setText(count);
        }


        //when ok button is clicked, card must be inserted into database and
        //listed in home xml file and go to home.class
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_cardNo = cardNo.getText().toString();
                saveNewCard(final_cardNo, cardName, cardImage);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                decisionAlert();
                //Add alertdialog to ask user "Are you sure?"

            }
        });


        scanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Get_Card_Details.this, ScannerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        numberKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNo.setInputType(InputType.TYPE_CLASS_NUMBER);
                description.setInputType(InputType.TYPE_CLASS_NUMBER);

                numberKeyboard.setVisibility(View.GONE);
                fullKeyboard.setVisibility(View.VISIBLE);
            }
        });

        fullKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNo.setInputType(InputType.TYPE_CLASS_TEXT);
                description.setInputType(InputType.TYPE_CLASS_TEXT);

                numberKeyboard.setVisibility(View.VISIBLE);
                fullKeyboard.setVisibility(View.GONE);
            }
        });
    }

    private void decisionAlert() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(Get_Card_Details.this);

        // Set the message show for the Alert time
        builder.setMessage("Your scan details will be lost permanently.");

        // Set Alert Title
        builder.setTitle("Are you sure?");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(true);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            Intent intent = new Intent(Get_Card_Details.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    private void saveNewCard(String passCardNo, String cardName, String cardImage) {

        if (passCardNo.equals("")) {

            cardNo.setError("* required");
            Toast.makeText(this, "Card details is missing.", Toast.LENGTH_SHORT).show();
        }else
        {
            AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

            DBModel dbModel = new DBModel();
            dbModel.cardNo = passCardNo;
            dbModel.cardName = cardName;
            dbModel.cardImage = cardImage;
            db.cardDao().insertUser(dbModel);

            Toast.makeText(this, "Card Saved: Success", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Get_Card_Details.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        decisionAlert();
    }
}