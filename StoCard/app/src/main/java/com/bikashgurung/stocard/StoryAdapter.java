package com.bikashgurung.stocard;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>{

    private List<ListAllModel> localDataSet = null;

    public StoryAdapter(List<ListAllModel> list) {
        localDataSet = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.story_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        ListAllModel listAllModel = localDataSet.get(position);

        //Picasso.get().load(listAllModel.getImage()).into(viewHolder.story_image);
        //Picasso is good for server but for local database, use of AppCompat is better than picasso.
        viewHolder.story_image.setImageDrawable(AppCompatResources.getDrawable(viewHolder.itemView.getContext(),listAllModel.getImage()));

        viewHolder.story_text.setText(listAllModel.getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display images in full screen and can scroll back and front.
                showImageInFullScreen(viewHolder, listAllModel);
            }
        });
    }

    private void showImageInFullScreen(ViewHolder holder, ListAllModel listAllModel) {
        final Dialog MyDialog = new Dialog(holder.itemView.getContext());
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.story_full_screen);

        TextView title = (TextView) MyDialog.findViewById(R.id.imageTitle);
        title.setText(listAllModel.getName());

        ImageView image = (ImageView) MyDialog.findViewById(R.id.fullImage);
        image.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.getContext(),listAllModel.getImage()));

        MyDialog.show();
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CircularImageView story_image;
        private final TextView story_text;

        public ViewHolder(View view) {
            super(view);

            story_image = (CircularImageView) view.findViewById(R.id.story_image);
            story_text = (TextView) view.findViewById(R.id.story_text);
        }

        public CircularImageView getStory_image() {
            return story_image;
        }

        public TextView getStory_text() {
            return story_text;
        }

    }
}
