package com.bikashgurung.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class forgotPassword extends Fragment {

    View view;
    MaterialButton back;

    public forgotPassword() {
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
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        back = (MaterialButton) view.findViewById(R.id.backToLogin);
        back.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.loginFragment));

        return view;
    }
}