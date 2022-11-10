package com.bikashgurung.stocard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.Locale;


public class ScannerActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 112;
    String name, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){

            //Extract the data…
             name = bundle.getString("cardName");
             image = bundle.getString("cardImage");

        }


        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

    }

    public void checkPermission(String permission, int requestCode) {

        //Checking if permission is granted or not.
        if (ContextCompat.checkSelfPermission(ScannerActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            //Take Permission
            ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(ScannerActivity.this, "Permission already granted.", Toast.LENGTH_SHORT).show();
            startScan();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted.", Toast.LENGTH_SHORT).show();
                startScan();
            } else {
                Intent intent = new Intent(ScannerActivity.this, AddCards.class);
                startActivity(intent);
                Toast.makeText(this, "Camera Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startScan() {

        GmsBarcodeScannerOptions optionsBuilder = new GmsBarcodeScannerOptions.Builder()
                .build();

        GmsBarcodeScanner gmsBarcodeScanner =
                GmsBarcodeScanning.getClient(this, optionsBuilder);

        gmsBarcodeScanner
                .startScan()
                .addOnSuccessListener(barcode -> OnSuccessListener(barcode))
                .addOnFailureListener(
                        e -> OnFailureListener())
                .addOnCanceledListener(
                        () -> OnFailureListener());

    }

    public void OnSuccessListener(Barcode barcode) {
        String cardNo = barcode.getDisplayValue();

        Intent intent = new Intent(ScannerActivity.this, Get_Card_Details.class);
        //Create the bundle
        Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
        bundle.putString("cardNo", cardNo);
        bundle.putString("cardName", name);
        bundle.putString("cardImage", String.valueOf(image));

//Add the bundle to the intent
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
    }

    public void OnFailureListener() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Card scan is failed. Would you like to add card manually?");

        // Set Alert Title
        builder.setTitle("Scan Failed");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            Intent intent = new Intent(ScannerActivity.this, Get_Card_Details.class);
            //Create the bundle
            Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
            bundle.putString("cardName", name);
            bundle.putString("cardImage", image);
//Add the bundle to the intent
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
            Intent intent = new Intent(ScannerActivity.this, AddCards.class);
            startActivity(intent);
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void OnCancelListener() {
        Intent intent = new Intent(ScannerActivity.this, AddCards.class);
        startActivity(intent);
        Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_SHORT).show();
    }
}