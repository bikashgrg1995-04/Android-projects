package com.bikashgurung.stocard;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.DBModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

public class Show_Card_Details extends AppCompatActivity {

    ImageView cardIMage, back, frontImage, backImage;
    TextView cardNo,front_TV, backTV, title, title1;
    String cardId, card_No,cardName, cardImageString;
    ImageFilterView QR;
    RelativeLayout frontLayout, backLayout;
    CardView notes, stores;

    Bitmap frontBitmap, backBitmap;

    ActivityResultLauncher<Intent> mCaptureFrontImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result != null) {
                front_TV.setVisibility(View.GONE);
            } else {
                front_TV.setVisibility(View.VISIBLE);
            }

            //we got the result
            Bundle bundle = result.getData().getExtras();
            frontBitmap = (Bitmap) bundle.get("data");
            frontImage.setImageBitmap(frontBitmap);
        }
    });

    ActivityResultLauncher<Intent> mCaptureBackImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result != null) {
                backTV.setVisibility(View.GONE);
            } else {
                backTV.setVisibility(View.VISIBLE);
            }

            //we got the result
            Bundle bundle = result.getData().getExtras();
            backBitmap = (Bitmap) bundle.get("data");
            backImage.setImageBitmap(backBitmap);

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_card_details);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            //Extract the data…
            cardId = bundle.getString("cardId");
        }

        cardIMage = findViewById(R.id.show_card_image);
        cardNo = findViewById(R.id.show_cardNumber);
        QR = findViewById(R.id.showQR);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Show_Card_Details.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        title1 = findViewById(R.id.store_name);

        frontImage = findViewById(R.id.front_camera);
        backImage = findViewById(R.id.back_camera);
        front_TV = findViewById(R.id.tvFront);
        backTV = findViewById(R.id.tvBack);
        title = findViewById(R.id.tvTitle);
        frontLayout = findViewById(R.id.front_layout);
        frontLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mCaptureFrontImage.launch(intent);
            }
        });

        backLayout = findViewById(R.id.back_layout);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mCaptureBackImage.launch(intent);
            }
        });

        notes = findViewById(R.id.notesCardView);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Show_Card_Details.this, NoteEditorActivity.class);
                intent.putExtra("card_id", cardId);
                startActivity(intent);
            }
        });

        stores = findViewById(R.id.storeCardView);
        stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Show_Card_Details.this, Store.class);

                intent.putExtra("card_id", cardId);
                intent.putExtra("card_name", cardName);

                startActivity(intent);

            }
        });

        getDetails();

    }


    private void generateQR(String card_No) {

        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();

        try {

            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(card_No, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            QR.setImageBitmap(mBitmap);//Setting generated QR code to imageView

        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    private void getDetails() {

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        DBModel cardDetail = db.cardDao().findById(cardId);
        card_No = cardDetail.getCardNo();
        cardName = cardDetail.getCardName();
        cardImageString = cardDetail.getCardImage();

        if (cardDetail != null){
            cardNo.setText(card_No);
            title.setText(cardName);
            title1.setText(cardName);
            //Picasso.get().load(cardImageString).into(cardIMage);
            cardIMage.setImageDrawable(AppCompatResources.getDrawable(this,Integer.parseInt(cardImageString)));

            generateQR(card_No);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}