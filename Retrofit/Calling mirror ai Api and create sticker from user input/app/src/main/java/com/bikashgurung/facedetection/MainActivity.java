package com.bikashgurung.facedetection;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView selectOption, selectStickerOption;
    CircularImageView imageView;
    MaterialButton convert, create, reset;
    String style, id = null;
    Uri uri;
    ProgressBar progressBar, progressBar1;
    ImageView stickerImage, convertedImage;
    TextView text;
    TextInputLayout layout;
    ActivityResultLauncher<String> mGetImage;
    ActivityResultLauncher<Intent> mCaptureImage;
    private Face face = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetImage = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {

                        imageView.setImageURI(result);
                        uri = result;

                        if (uri != null) {
                            text.setVisibility(View.GONE);
                        } else {
                            text.setVisibility(View.VISIBLE);
                        }

                    }
                }
        );

        mCaptureImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {

                if (result != null) {
                    text.setVisibility(View.GONE);
                } else {
                    text.setVisibility(View.VISIBLE);
                }

                //we got the result
                Bundle bundle = result.getData().getExtras();
                Bitmap imageBitmap = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(imageBitmap);
                System.out.println(uri);
            }
        });

        //UI reference of textView
        selectOption = findViewById(R.id.select);
        selectStickerOption = findViewById(R.id.selectStickerType);
        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progress_circular);
        progressBar1 = findViewById(R.id.progress_circular1);

        layout = findViewById(R.id.textviewlayout1);

        initUI();

        imageView = findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        stickerImage = findViewById(R.id.imageSticker);
        convertedImage = findViewById(R.id.imageConverted);

        convert = findViewById(R.id.btnConvert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertImage();
            }
        });

        create = findViewById(R.id.btnSticker);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSticker();
            }
        });

        reset = findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri emptyUri = null;

                selectOption.setText("");

                selectStickerOption.setText("");

                selectStickerOption.setEnabled(false);

                imageView.setImageURI(null);
                convertedImage.setImageURI(null);
                if (convertedImage == null) {

                } else {
                    Picasso.get().load(emptyUri).into(convertedImage);
                }

                text.setVisibility(View.VISIBLE);


                stickerImage.setImageURI(null);

                if (stickerImage == null) {

                } else {
                    Picasso.get().load(emptyUri).into(stickerImage);
                }

                layout.setEnabled(false);
                create.setEnabled(false);
            }
        });

    }

    private void createSticker() {
        progressBar1.setVisibility(View.VISIBLE);
        String face_id, sticker, loc;

        face_id = id;
        sticker = selectStickerOption.getText().toString();
        loc = "en";

        System.out.println(sticker);

        Call<StickerResult> call = RetrofitClient.getInstance().getApi().createSticker(face_id, sticker, loc);
        call.enqueue(new Callback<StickerResult>() {
            @Override
            public void onResponse(Call<StickerResult> call, Response<StickerResult> response) {
                StickerResult responseData = response.body();
                System.out.println(responseData);

                String imageUrl = responseData.getUrl();

                System.out.println(imageUrl);
                //Picasso.get().load(imageUrl).into(stickerImage);
                Picasso.get().load(imageUrl).into(stickerImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar1.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar1.setVisibility(View.GONE);
                        System.out.println(e.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<StickerResult> call, Throwable t) {

            }
        });
    }

    private void convertImage() {

        progressBar.setVisibility(View.VISIBLE);
        style = selectOption.getText().toString();

        if (uri == null) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Please select Image....", Toast.LENGTH_SHORT).show();
        } else {

            System.out.println(uri);
            System.out.println(style);


            // use the FileUtils to get the actual file by uri
            File file = savefile(uri);

            System.out.println(file);

            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            System.out.println(requestFile);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

            // finally, execute the request
            Call<Result> call = RetrofitClient.getInstance().getApi().getImage(style, body);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result responseData = response.body();

                    Boolean ok = responseData.isOk();
                    System.out.println(ok);

                    if (responseData != null) {

                        progressBar.setVisibility(View.GONE);

                        face = responseData.getFace();
                        String faceId = face.getId();
                        String imageUrl = face.getUrl();

                        Picasso.get().load(imageUrl).into(convertedImage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                selectStickerOption.setEnabled(true);
                                layout.setEnabled(true);
                                create.setEnabled(true);
                            }

                            @Override
                            public void onError(Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                Log.i("error", e.getMessage());
                            }
                        });
                        id = faceId;
                    }


                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    System.out.println(t.getMessage());
                    progressBar.setVisibility(View.GONE);
                }
            });

        }
    }

    private File savefile(Uri imageUri) {
        //create a file to write bitmap data
        File f = new File(getCacheDir(), "upload.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mCaptureImage.launch(intent);

                } else if (options[item].equals("Choose from Gallery")) {
                    mGetImage.launch("image/*");
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void initUI() {

        // create list of Options
        ArrayList<String> customerList = getList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, customerList);

        //Set adapter
        selectOption.setAdapter(adapter);

        // create list of Options
        ArrayList<String> stickerList = getStickerList();

        //Create adapter
        ArrayAdapter<String> stickerAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, stickerList);

        //Set adapter
        selectStickerOption.setAdapter(stickerAdapter);
    }

    private ArrayList<String> getList() {
        ArrayList<String> options = new ArrayList<>();
        options.add("kenga");
        options.add("anime");
        return options;
    }

    private ArrayList<String> getStickerList() {
        ArrayList<String> options = new ArrayList<>();

        options.add("im_perfect_anime");
        options.add("im_cool_hero_anime");

        return options;
    }

}