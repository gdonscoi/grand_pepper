package net.grandpepper.caiena.grandpepper.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.grandpepper.caiena.grandpepper.R;
import net.grandpepper.caiena.grandpepper.activity.GrandPepperDetailActivity;
import net.grandpepper.caiena.grandpepper.beans.CallForPeppers;
import net.grandpepper.caiena.grandpepper.beans.GrandPepper;
import net.grandpepper.caiena.grandpepper.util.AndroidSystemUtil;

import java.util.ArrayList;
import java.util.List;

public class AdapterGrandPepper extends RecyclerView.Adapter<AdapterGrandPepper.ViewHolder> {

    private List<GrandPepper> dataList;
    private Context context;

    @SuppressWarnings("unchecked")
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView descriptionCard;
        public ImageView imageBackgroundCard;
        public RelativeLayout container;
        private Context context;
        private List<GrandPepper> dataList;

        public ViewHolder(View v, Context context, List<GrandPepper> dataList) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.container_main_card);
            descriptionCard = (TextView) container.findViewById(R.id.text_description_card);
            imageBackgroundCard = (ImageView) container.findViewById(R.id.image_background_card);
            this.context = context;
            this.dataList = dataList;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GrandPepperDetailActivity.class);
            intent.putExtra("title", ((TextView) view.findViewById(R.id.text_description_card)).getText());
            intent.putExtra("background_image", dataList.get(getAdapterPosition()).backgroundImagePath);
            intent.putExtra("grand_pepper", dataList.get(getAdapterPosition()));

            Pair<View, String> p1 = Pair.create(view.findViewById(R.id.image_background_card), "comum_image");
            Pair<View, String> p2 = Pair.create(view.findViewById(R.id.text_description_card), "comum_text");
//            Pair<View, String> p3 = Pair.create(view.findViewById(R.id.content_background_text), "comum_background_text");

            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, p1, p2);
            context.startActivity(intent, options.toBundle());
        }
    }

    public AdapterGrandPepper(List<GrandPepper> dataList, Context context) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public AdapterGrandPepper.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_card_view, parent, false);

        return new ViewHolder(v, context,dataList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.descriptionCard.setText(dataList.get(position).title);
        Bitmap backgroundImage = AndroidSystemUtil.getImageExternalStorage(dataList.get(position).backgroundImagePath);
        if(backgroundImage != null)
            holder.imageBackgroundCard.setImageBitmap(backgroundImage);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
