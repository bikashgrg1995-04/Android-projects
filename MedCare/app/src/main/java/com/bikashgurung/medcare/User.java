package com.bikashgurung.medcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;

public class User extends Fragment {

    View view;
    Button logout, edit;
    MaterialTextView username;
    MaterialTextView username1;
    MaterialTextView email;
    MaterialTextView name;
    MaterialTextView phone;
    MaterialTextView bloodType;
    MaterialTextView address;
    MaterialTextView status;
    String Sname, Semail, emailValue, Sphone, Sblood, Saddress, Sstatus;

    public User() {
        // Required empty public constructor
    }


    @SuppressLint("CutPasteId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);

        username = view.findViewById(R.id.txtUsername);
        username1 = view.findViewById(R.id.txtUsername1);
        email = view.findViewById(R.id.tvEmail);
        name = view.findViewById(R.id.tvFullName);
        phone = view.findViewById(R.id.tvPhone);
        bloodType = view.findViewById(R.id.tvFullName);
        address = view.findViewById(R.id.tvAddress);
        status = view.findViewById(R.id.tvStatus);


        String usernameValue = getActivity().getIntent().getStringExtra("username");
        if (!usernameValue.equals(""))
        {
            username.setVisibility(View.INVISIBLE);
            username1.setVisibility(View.VISIBLE);
            username1.setText(usernameValue);
        }

        emailValue = getActivity().getIntent().getStringExtra("email");
        if (!emailValue.equals(""))
        {
               email.setText(emailValue);
        }

        logout = view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Logout Success..", Toast.LENGTH_SHORT).show();
        });

        edit = view.findViewById(R.id.btnEdit);
        edit.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            intent.putExtra("email", emailValue);
            startActivity(intent);
            Toast.makeText(getContext(), "Edit your profile details here..", Toast.LENGTH_SHORT).show();
        });


        return view;
    }


}