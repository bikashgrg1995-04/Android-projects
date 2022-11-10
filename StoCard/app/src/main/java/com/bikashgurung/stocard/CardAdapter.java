package com.bikashgurung.stocard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bikashgurung.stocard.DB.DBModel;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private List<DBModel> cardList;

    public CardAdapter(Context context) {
        this.context = context;
    }

    public void setCardList(List<DBModel> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {

        int id = this.cardList.get(position).getUid();
        int imageUrl =Integer.parseInt(this.cardList.get(position).cardImage);

        //Picasso.get().load(imageUrl).into(holder.card_image);

        //Picasso is good for server but for local database, use of AppCompat is better than picasso.
        holder.card_image.setImageDrawable(AppCompatResources.getDrawable(context, imageUrl));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Show_Card_Details.class);
//                //Create the bundle
                Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                bundle.putString("cardId", String.valueOf(id));
//Add the bundle to the intent
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView card_image;

        public ViewHolder(View view) {
            super(view);
            card_image = (ImageView) view.findViewById(R.id.card);
        }
    }

    // method for filtering our recyclerview items.
    public void filterList(List<DBModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        cardList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

}
