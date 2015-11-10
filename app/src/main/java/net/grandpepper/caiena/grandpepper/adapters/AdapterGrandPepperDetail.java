package net.grandpepper.caiena.grandpepper.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.activity.DetailTalksActivity;

import java.util.ArrayList;

public class AdapterGrandPepperDetail extends RecyclerView.Adapter<AdapterGrandPepperDetail.ViewHolder> {

    private ArrayList<String> dataList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView descriptionCard;
        public RelativeLayout container;
        private Context context;

        public ViewHolder(View v, Context context) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.container_main_card);
            descriptionCard = (TextView) container.findViewById(R.id.text_description_card);
            this.context = context;
            v.setOnClickListener(this);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailTalksActivity.class);
            intent.putExtra("title", ((TextView) view.findViewById(R.id.text_description_card)).getText());
            Pair<View, String> p1 = Pair.create(view.findViewById(R.id.image_background_card), "comum_image_owner");
            Pair<View, String> p2 = Pair.create(view.findViewById(R.id.text_description_card), "name_owner");
            Pair<View, String> p3 = Pair.create(view.findViewById(R.id.text_description_owner), "comum_description_owner");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1, p2, p3);
            context.startActivity(intent, options.toBundle());
        }
    }

    public AdapterGrandPepperDetail(ArrayList<String> repositories, Context context) {
        this.context = context;
        this.dataList = repositories;
    }

    @Override
    public AdapterGrandPepperDetail.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.talks_card_view, parent, false);

        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.descriptionCard.setText(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
