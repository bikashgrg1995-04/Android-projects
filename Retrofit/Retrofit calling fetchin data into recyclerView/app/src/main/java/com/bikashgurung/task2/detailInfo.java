package com.bikashgurung.task2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit2.Call;

public class detailInfo extends Fragment {

    CircularImageView imageView;
    MaterialTextView id;
    MaterialTextView fName;
    MaterialTextView lName;
    MaterialTextView email;
    MaterialButton back;
    ProgressBar progressBar;

    String getId, getFName, getLName, getEmail, getAvatar;


    public detailInfo() {
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
        View view =  inflater.inflate(R.layout.fragment_detail_info, container, false);

        progressBar = view.findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);

         getId = getArguments().getString("id");
         getFName = getArguments().getString("first_name");
         getLName = getArguments().getString("last_name");
         getEmail = getArguments().getString("user_email");
         getAvatar = getArguments().getString("user_avatar");

        imageView = view.findViewById(R.id.userImage);
        Picasso.get().load(getAvatar).resize(50, 50).
                centerCrop().into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        id = view.findViewById(R.id.userID);
        id.setText(getId);

        fName = view.findViewById(R.id.userFname);
        fName.setText(getFName);

        lName = view.findViewById(R.id.userLname);
        lName.setText(getLName);

        email = view.findViewById(R.id.userEmail);
        email.setText(getEmail);

        back = view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.listUsers);

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}