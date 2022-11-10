package com.bikashgurung.stocard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {

    private List<ListAllModel> localDataSet = null;

    public AllAdapter(List<ListAllModel> localDataSet) {
        this.localDataSet = localDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_all_cards_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        ListAllModel listAllModel = localDataSet.get(position);
        Picasso.get().load(listAllModel.getImage()).into(holder.card_image);
        holder.card_name.setText(listAllModel.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScannerActivity.class);
                //Create the bundle
                Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                bundle.putString("cardName", listAllModel.getName());
                bundle.putString("cardImage", String.valueOf(listAllModel.getImage()));
//Add the bundle to the intent
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView card_image;
        private final TextView card_name;

        public ViewHolder(View view) {
            super(view);
            card_image = (ImageView) view.findViewById(R.id.card_image);
            card_name = (TextView) view.findViewById(R.id.card_title);
        }

        public ImageView getCard_image() {
            return card_image;
        }

        public TextView getCard_name() {
            return card_name;
        }
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<ListAllModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        localDataSet = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
