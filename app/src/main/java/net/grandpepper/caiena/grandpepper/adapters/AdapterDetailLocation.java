package net.grandpepper.caiena.grandpepper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.beans.Location;

import java.util.List;

public class AdapterDetailLocation extends RecyclerView.Adapter<AdapterDetailLocation.ViewHolder> {

    private List<Location> locations;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameLocation;
        public TextView addressLocation;
        public TextView actionLocation;

        public ViewHolder(View v) {
            super(v);
            nameLocation = (TextView) v.findViewById(R.id.name_location);
            addressLocation = (TextView) v.findViewById(R.id.address_location);
            actionLocation = (TextView) v.findViewById(R.id.action_location);
        }
    }

    public AdapterDetailLocation(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public AdapterDetailLocation.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_location_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameLocation.setText(locations.get(position).name);
        holder.addressLocation.setText(locations.get(position).address);
        holder.actionLocation.setText(locations.get(position).action);
    }


    @Override
    public int getItemCount() {
        return locations.size();
    }

}
