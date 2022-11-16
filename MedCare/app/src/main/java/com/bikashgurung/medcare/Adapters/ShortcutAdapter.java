package com.bikashgurung.medcare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bikashgurung.medcare.Models.Shortcut;
import com.bikashgurung.medcare.R;

import java.util.ArrayList;

public class ShortcutAdapter extends ArrayAdapter<Shortcut> {


    public ShortcutAdapter(@NonNull Context context, ArrayList<Shortcut> shortcutModelArrayList) {
        super(context, 0, shortcutModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.shortcut_item, parent, false);
        }

        Shortcut shortcutModel = getItem(position);

        TextView shortcutTV = listitemView.findViewById(R.id.shortcutTitle);
        ImageView shortcutIV = listitemView.findViewById(R.id.shortcutImage);

        shortcutTV.setText(shortcutModel.getTitle());
        shortcutIV.setImageResource(shortcutModel.getImage());
        return listitemView;
    }
}
