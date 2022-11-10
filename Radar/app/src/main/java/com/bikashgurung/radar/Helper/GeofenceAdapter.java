package com.bikashgurung.radar.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bikashgurung.radar.R;
import com.bikashgurung.radar.Retrofit.Model.List_Geofences.Example;
import com.bikashgurung.radar.Retrofit.Model.List_Geofences.Geofence;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class GeofenceAdapter extends RecyclerView.Adapter<GeofenceAdapter.ViewHolder>{

    private List<Geofence> localDataSet = null;

    public GeofenceAdapter(List<Geofence> localDataSet) {
        this.localDataSet = localDataSet;
    }

    @NonNull
    @Override
    public GeofenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_geofence_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeofenceAdapter.ViewHolder holder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Geofence geofence = localDataSet.get(position);
        String name = geofence.getDescription();
        String tag = geofence.getTag();

        holder.getName().setText(name);
        holder.getTag().setText(tag);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView name;
        private final MaterialTextView tag ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (MaterialTextView) itemView.findViewById(R.id.geofenceName);
            tag = (MaterialTextView) itemView.findViewById(R.id.geofenceTag);
        }

        public MaterialTextView getName() {
            return name;
        }

        public MaterialTextView getTag() {
            return tag;
        }
    }
}
