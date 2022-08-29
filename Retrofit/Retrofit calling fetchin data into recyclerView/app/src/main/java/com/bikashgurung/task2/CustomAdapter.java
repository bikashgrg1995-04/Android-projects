package com.bikashgurung.task2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Callback;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Users> localDataSet = null;

    public CustomAdapter(List<Users> usersList) {
        localDataSet = usersList;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView tvId;
        private final MaterialTextView tvFname;
        private final MaterialTextView tvLname;
        private final MaterialTextView tvEmail;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvId = (MaterialTextView) view.findViewById(R.id.id);
            tvFname = (MaterialTextView) view.findViewById(R.id.firstName);
            tvLname = (MaterialTextView) view.findViewById(R.id.lastName);
            tvEmail = (MaterialTextView) view.findViewById(R.id.email);
        }

        public MaterialTextView getTvId() {
            return tvId;
        }

        public MaterialTextView getTvFname() {
            return tvFname;
        }

        public MaterialTextView getTvLname() {
            return tvLname;
        }

        public MaterialTextView getTvEmail() {
            return tvEmail;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Users user = localDataSet.get(position);

        viewHolder.getTvId().setText(String.valueOf(user.getId()));
        viewHolder.getTvFname().setText(user.getFirstName());
        viewHolder.getTvLname().setText(user.getLastName());
        viewHolder.getTvEmail().setText(user.getEmail());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(user.getId());
                String fname = user.getFirstName();
                String lname = user.getLastName();
                String email = user.getEmail();
                String avatar = user.getAvatar();

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("first_name", fname);
                bundle.putString("last_name", lname);
                bundle.putString("user_email", email);
                bundle.putString("user_avatar", avatar);

                Navigation.findNavController(view).navigate(R.id.detailInfo, bundle);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}